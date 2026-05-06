# Java-додаток раптово кидає OutOfMemoryError. Як ти будеш дебажити?
Причина
Купа (Heap) заповнена. Або витік пам'яті, або об'єктів більше ніж ліміт.
Як дебажити
1. Запусти з прапорами: -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heap.hprof
2. Відкрий heap dump у Eclipse MAT або VisualVM
3. Знайди 'Dominator Tree' — що займає найбільше пам'яті?
4. Перевір чи є великі колекції (List, Map) що ніколи не очищуються
5. Збільш heap тимчасово: -Xmx2g — якщо помогло, є витік
Вирішення
Знайди клас що тримає об'єкти довше ніж потрібно. Виправ: очищай колекції, закривай з'єднання, використовуй WeakReference для кешу.

# Бачиш високе CPU але мало трафіку. Що може бути причиною?
Причина
Зазвичай: нескінченний цикл, busy-wait, надмірний GC, або гарячий шлях коду (hot path).
Як дебажити
1. jstack кілька разів поспіль — знайди потоки що постійно RUNNABLE
2. Якщо GC thread займає CPU → перевір GC логи: -Xlog:gc
3. Профілюй з async-profiler або JFR (Java Flight Recorder)
4. Знайди hot method — де CPU проводить найбільше часу
5. Перевір регулярні вирази (regex) — вони можуть бути catastrophic
Вирішення
Усунь нескінченний цикл. Якщо GC — оптимізуй алокацію об'єктів. Якщо regex — перекомпілюй і кешуй Pattern.

# Потік застряг у стані BLOCKED. Як знайти і виправити?
Причина
Потік BLOCKED = він чекає на монітор (lock) який тримає інший потік.
Як дебажити
1. jstack → знайди рядок: 'waiting to lock <0x...>'
2. Знайди хто тримає цей lock: 'locked <0x...>'
3. Переглянь що той потік робить — чи він теж чогось чекає?
4. Якщо два потоки чекають один одного — це deadlock
5. Перевір чи lock утримується занадто довго (DB call всередині synchronized?)
Вирішення
Винеси I/O і DB виклики за межі synchronized блоку. Використовуй Lock з таймаутом: lock.tryLock(timeout). Або замін на ConcurrentHashMap замість synchronized Map.

# Додаток сповільнюється через кілька годин роботи. Що перевіриш?
Причина
Класичні причини: витік пам'яті (heap росте), накопичення з'єднань, memory fragmentation, або GC пауз стає більше.
Як дебажити
1. Моніторь heap через JMX або Prometheus кожні 5 хвилин
2. Перевір чи heap росте і ніколи не падає після GC — витік!
3. Перевір кількість відкритих з'єднань (DB, HTTP) — чи закриваються?
4. Перевір thread pool — чи не накопичуються задачі в черзі?
5. Переглянь GC логи — чи Full GC трапляється частіше з часом?
Вирішення
Знайди джерело витоку (heap dump). Переконайся що всі ресурси закриваються в finally або try-with-resources. Налаштуй connection pool (HikariCP).

# Часті GC паузи. Як оптимізувати?
Причина
Stop-the-world GC паузи = вся JVM зупиняється. Причина: багато живих об'єктів, малий heap, неправильний GC алгоритм.
Як дебажити
1. Включи GC логи: -Xlog:gc*
2. Аналізуй: як довго паузи? Як часто Full GC?
3. Збільш heap: -Xmx (але не більше RAM - OS)
4. Переключись на G1GC: -XX:+UseG1GC або ZGC для low-latency
5. Зменш алокацію: уникай нових об'єктів у hot path (StringBuilder замість +)
6. Збільш Eden space: -XX:NewRatio=2
Вирішення
G1GC або ZGC для latency-sensitive додатків. Зменш short-lived об'єкти. Object pooling для важких об'єктів.

# HashMap спричиняє проблеми з продуктивністю під навантаженням. Чому?
Причина
HashMap не thread-safe! Під навантаженням: data corruption, infinite loop (Java 6), або ConcurrentModificationException.
Як дебажити
1. Перевір чи HashMap використовується з кількох потоків
2. Якщо так — це race condition: два потоки можуть змінювати bucket одночасно
3. В Java 6: може виникнути infinite loop при resize через circular reference
4. Перевір CPU — якщо 100% на одному потоці → infinite loop у HashMap
5. Профілюй: де саме застрягає?
Вирішення
Заміни на ConcurrentHashMap — thread-safe, lock-striping для паралелізму. Або Collections.synchronizedMap() якщо потрібна повна синхронізація. Ніколи не використовуй HashMap з кількох потоків!

# Кілька потоків оновлюють спільні дані неправильно. Як виправити?
Причина
Race condition: два потоки читають-змінюють-пишуть одночасно. Результат непередбачуваний.
Як дебажити
1. Знайди спільну змінну (shared mutable state)
2. Переконайся що операція не атомарна: count++ = read + increment + write = 3 кроки!
3. Постав breakpoint або логування — чи значення некоректне?
4. Перевір чи є volatile, synchronized, або AtomicInteger
Вирішення
1) Використовуй AtomicInteger/AtomicLong для лічильників. 2) synchronized метод або блок для складних операцій. 3) ReentrantLock для тонкого контролю. 4) Найкраще — зроби змінну immutable або local.

# API добре працює локально але падає на продакшні. Що досліджуватимеш?
Причина
Різниця між локальним і продакшн середовищем: конфіги, дані, мережа, ресурси.
Як дебажити
1. Порівняй env variables: DB URL, API keys, timeouts
2. Перевір logs на prod — точне повідомлення про помилку
3. Перевір network: чи prod може досягнути зовнішніх сервісів?
4. Перевір розмір даних: локально маленький датасет, на prod — великий?
5. Перевір timeouts: connection timeout, read timeout
6. Перевір SSL/TLS certificates — чи не прострочені?
Вирішення
Стандартизуй конфіги через environment variables. Додай детальне логування. Використовуй feature flags для поступового rollout.

# Підозрюєш витік пам'яті. Як підтвердити?
Причина
Витік = об'єкти живуть довше ніж потрібно бо хтось тримає на них посилання.
Як дебажити
1. Моніторь heap через JConsole або Prometheus — чи росте після кожного GC?
2. Якщо heap after GC = постійно росте → підтверджено витік
3. Візьми heap dump: jmap -dump:format=b,file=heap.hprof
4. Відкрий в Eclipse MAT: 'Leak Suspects Report'
5. Знайди об'єкти яких дуже багато і вони тримають ланцюжок посилань до GC root
Вирішення
Типові джерела: static колекції, listeners що не відписані, ThreadLocal що не очищується, unclosed streams/connections.

# Сервіс стає недоступним випадково. Що може відбуватися?
Причина
Причини: Full GC stop-the-world, deadlock, thread pool exhaustion, або зовнішній сервіс завис.
Як дебажити
1. Перевір GC логи: чи є довгі Full GC паузи (>1 сек)?
2. jstack під час інциденту — чи всі потоки BLOCKED або WAITING?
3. Перевір thread pool queue — чи задачі накопичуються?
4. Перевір зовнішні залежності: DB, cache, API — чи вони відповідають?
5. Перевір OS: swap usage, file descriptors limit
Вирішення
Налаштуй circuit breaker (Resilience4j) для зовнішніх сервісів. Налаштуй timeouts на всіх IO операціях. Переключись на ZGC для мінімізації GC пауз.

# Бачиш deadlock у системі. Як виявити та вирішити?
Причина
Deadlock: потік A тримає lock1 і чекає lock2. Потік B тримає lock2 і чекає lock1. Обидва чекають вічно.
Як дебажити
1. jstack → шукай 'Found one Java-level deadlock'
2. JMX: ThreadMXBean.findDeadlockedThreads()
3. Побудуй граф залежностей: хто тримає що і чого чекає
4. Знайди цикл у графі — це і є deadlock
Вирішення
1) Завжди бери locks в одному порядку (lock1 завжди перед lock2). 2) Використовуй tryLock(timeout) замість lock(). 3) Використовуй один lock замість кількох. 4) Розглянь lock-free структури (ConcurrentHashMap, AtomicInteger).

# Логи показують непослідовну поведінку між запитами. Чому?
Причина
Причини: shared mutable state між запитами, race condition, або неправильне використання ThreadLocal.
Як дебажити
1. Перевір чи є статичні змінні що змінюються під час запитів
2. Перевір Spring beans — чи є mutable поля в singleton beans?
3. Перевір ThreadLocal — чи очищується після кожного запиту?
4. Додай request ID в логи (MDC) щоб відстежувати кожен запит окремо
5. Перевір чи кешовані дані інвалідуються правильно
Вирішення
Зроби beans stateless. Очищуй ThreadLocal в finally. Використовуй MDC (Mapped Diagnostic Context) для correlation ID в логах.

# Додаток падає без чіткої помилки. Як дебажити?
Причина
Можливо: OOM Killer (Linux вбив процес), JVM crash (native код), або stack overflow.
Як дебажити
1. Перевір /var/log/syslog або dmesg: 'Out of memory: Kill process'
2. Перевір JVM crash log: hs_err_pid.log у робочій директорії
3. Перевір exit code: code 137 = killed by OS (OOM killer)
4. Перевір stack traces на StackOverflowError — нескінченна рекурсія?
5. Запусти з -XX:+ExitOnOutOfMemoryError для чіткого сигналу
Вирішення
Якщо OOM Killer — збільш RAM або зменш heap. Якщо StackOverflow — знайди нескінченну рекурсію. Якщо JVM crash — оновлюй JVM або знайди проблемний native код.

# DB виклик сповільнює Java сервіс. Як оптимізувати?
Причина
Причини: повільний SQL, N+1 запит, відсутній індекс, або connection pool exhaustion.
Як дебажити
1. Логуй повільні запити: spring.jpa.show-sql=true + p6spy
2. Знайди N+1: якщо бачиш 100 SELECT для 100 об'єктів — це N+1
3. EXPLAIN ANALYZE на повільний SQL у DB
4. Перевір чи є індекси на WHERE та JOIN колонках
5. Перевір HikariCP метрики: чи connection pool не вичерпується?
Вирішення
N+1 → JOIN FETCH або @EntityGraph. Повільний SQL → додай індекс. Connection exhaustion → збільш pool або зменш час утримання з'єднання. Великі дані → пагінація.

# Thread pool вичерпується під навантаженням. Який підхід?
Причина
Задачі надходять швидше ніж виконуються. Черга заповнюється → RejectedExecutionException.
Як дебажити
1. Моніторь: ThreadPoolExecutor.getActiveCount(), getQueue().size()
2. Знайди чому задачі виконуються повільно — блокуючий I/O?
3. Перевір чи є thread що зависають (BLOCKED на DB/HTTP)
4. Перевір розмір черги: чи не нескінченна черга ховає проблему?
Вирішення
1) Розділи пули: окремий для CPU-bound, окремий для I/O-bound задач. 2) Для I/O — використовуй async/reactive (CompletableFuture, WebFlux). 3) Налаштуй timeouts на I/O операціях. 4) Додай back-pressure: обмеж чергу і відхиляй надмірні запити.

# Потрібно безпечно обробляти високу конкурентність. Що використаєш?
Причина
При high concurrency: race conditions, lock contention, або deadlocks якщо неправильно синхронізувати.
Як дебажити
1. Визнач тип операції: лічильник? колекція? складна транзакція?
2. Лічильники → AtomicInteger, AtomicLong, LongAdder
3. Колекції → ConcurrentHashMap, CopyOnWriteArrayList, BlockingQueue
4. Складні операції → ReentrantReadWriteLock (багато читань, мало записів)
5. Незалежні задачі → CompletableFuture, parallel streams
Вирішення
Prefer lock-free (Atomic*) > fine-grained locks > coarse-grained synchronized. Immutable objects = завжди thread-safe. Використовуй java.util.concurrent — не пиши свою синхронізацію.

# Система обробляє дублікати запитів. Як обробляти?
Причина
Дублікати виникають через retry логіку клієнта, мережеві збої, або 'at-least-once' delivery у Kafka.
Як дебажити
1. Знайди звідки дублікати: клієнт робить retry? Kafka? Scheduler?
2. Перевір чи операція idempotent (повторна операція = той самий результат)
3. Якщо ні → треба idempotency key
4. Логуй request ID та перевіряй чи вже оброблено
Вирішення
1) Idempotency key: клієнт надсилає унікальний ID, сервер зберігає і перевіряє. 2) DB unique constraint: INSERT IGNORE або ON CONFLICT DO NOTHING. 3) Redis SETNX для розподіленого дедуплікування. 4) Kafka exactly-once semantics.

# Кеш повертає застарілі дані. Як виправити?
Причина
Дані змінились у БД але кеш не знає про це. Або TTL занадто великий.
Як дебажити
1. Перевір TTL (time-to-live) — може занадто великий?
2. Перевір чи є cache invalidation при оновленні даних
3. Перевір race condition: write to DB та evict cache атомарні?
4. Перевір distributed cache (Redis) — чи всі інстанції синхронізовані?
Вирішення
1) Cache-aside: оновлюй DB → одразу видаляй з кешу (evict). 2) Write-through: оновлюй кеш і DB разом. 3) Зменш TTL для критичних даних. 4) Для strong consistency — використовуй cache з версіями або event-driven invalidation (Kafka).

# Додаток не масштабується навіть після додавання інстанцій. Чому?
Причина
Є bottleneck що не масштабується горизонтально: DB, shared state, або centralized lock.
Як дебажити
1. Знайди bottleneck: DB? Черга? Shared cache? External API?
2. Перевір чи DB є єдиним вузьким місцем (single point of contention)
3. Перевір чи є distributed lock що одночасно може тримати тільки 1 інстанція
4. Перевір чи є shared in-memory state (наприклад HashMap в одній інстанції)
5. Перевір load balancer — чи розподіляє рівномірно?
Вирішення
Масштабуй DB горизонтально (read replicas). Розподіли стан у Redis. Використовуй message queue (Kafka) замість синхронних викликів. Видали distributed locks або замін на optimistic locking.

# Потрібно відстежити запит через кілька шарів. Як це зробити?
Причина
У мікросервісах запит проходить через 5-10 сервісів. Без tracing — неможливо знайти де затримка або помилка.
Як дебажити
1. Згенеруй унікальний Trace ID на вході (gateway або перший сервіс)
2. Передавай Trace ID у HTTP headers: X-Trace-ID або W3C TraceContext
3. Логуй Trace ID у кожному сервісі через MDC: MDC.put('traceId', id)
4. Збирай spans у Zipkin або Jaeger через OpenTelemetry
5. Переглядай waterfall діаграму — де найдовша затримка?
Вирішення
Впровадь OpenTelemetry + Jaeger/Zipkin. Додай @Timed на ключові методи. Використовуй Spring Cloud Sleuth (автоматична інструментація). Логуй traceId у кожному лог-записі.