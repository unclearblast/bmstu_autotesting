
# 📘 Пособие для самых маленьких: как запустить проект и нагрузочный тест

Этот файл — пошаговая инструкция для человека, который впервые видит Docker, Gradle и k6.  
Выполняйте шаги строго по порядку, и всё заработает.

---

## 1. Что нужно установить?

Перед началом скачайте и установите следующие программы:

| Программа | Зачем | Ссылка для скачивания |
|-----------|-------|------------------------|
| **Docker Desktop** | Чтобы запустить базы данных и Kafka | https://www.docker.com/products/docker-desktop/ |
| **Java 17** | Чтобы запустить наши сервисы | https://adoptium.net/temurin/releases/ (выберите версию 17 для вашей ОС) |
| **Git** | Чтобы скачать проект (или можно скачать ZIP-архивом) | https://git-scm.com/downloads |
| **k6** | Чтобы запустить нагрузочный тест | https://k6.io/docs/getting-started/installation/ |

После установки **перезагрузите компьютер** (особенно важно для Docker).

---

## 2. Скачиваем проект

**Способ 1 – через Git (рекомендуется):**
```bash
git clone https://github.com/ВАШ-ЛОГИН/satellite-constellation-system.git
cd satellite-constellation-system
Способ 2 – ZIP-архив:
На странице репозитория нажмите зелёную кнопку «Code» → «Download ZIP», распакуйте папку и откройте её.

3. Запускаем инфраструктуру (базы данных и Kafka)
Важно: Docker Desktop должен быть запущен (в трее должна быть зелёная иконка с китом).

Откройте терминал (Command Prompt на Windows, Terminal на Mac/Linux) и перейдите в папку проекта:

bash
cd путь-к-папке/satellite-constellation-system
Теперь запустите контейнеры:

bash
docker-compose up -d
Появятся строки с «Starting ... done». Подождите 20–30 секунд, пока всё поднимется.

Проверка: выполните команду:

bash
docker ps
Вы должны увидеть 4 контейнера: postgres-space, postgres-telemetry, zookeeper, kafka. Все со статусом Up.

4. Запускаем первый микросервис (space-operation-center)
Откройте новый терминал (старый не закрывайте). Перейдите в папку проекта.

Windows:

bash
cd space-operation-center
gradlew.bat bootRun
Mac / Linux:

bash
cd space-operation-center
./gradlew bootRun
Будет долгая загрузка зависимостей (первый раз 2–3 минуты). В конце появится строчка:

text
Started SpaceOperationCenterApplication in X seconds
Это значит, сервис запущен на порту 8080.

Оставьте этот терминал открытым – он нужен для работы сервиса.

5. Запускаем второй микросервис (telemetry-service)
Откройте третий терминал. Снова перейдите в папку проекта.

Windows:

bash
cd telemetry-service
gradlew.bat bootRun
Mac / Linux:

bash
cd telemetry-service
./gradlew bootRun
Дождитесь надписи Started TelemetryApplication. Сервис запущен на порту 8081.

Оставьте и этот терминал открытым.

6. Устанавливаем k6 (инструмент для нагрузочного теста)
Если вы ещё не установили k6, сделайте это сейчас.

Windows: скачайте установщик с https://k6.io/docs/getting-started/installation/#windows

Mac: brew install k6

Linux: sudo apt-get install k6 (Ubuntu/Debian) или скачайте бинарник

Проверьте установку:

bash
k6 version
Должно показать k6 v0.48.0 или новее.

7. Запускаем нагрузочный тест
Откройте четвёртый терминал. Перейдите в папку с тестами:

bash
cd load-tests
Запустите тест (он займёт 2 минуты):

bash
k6 run --out html=reports/report.html load-test.js
Во время выполнения вы увидите в реальном времени:

количество виртуальных пользователей (оно растёт до 50)

количество запросов в секунду

процент ошибок

Когда тест закончится, появится таблица с итогами.

8. Смотрим отчёт
Откройте папку load-tests/reports/. Там лежит файл report.html. Дважды кликните по нему – он откроется в браузере.

В отчёте вы увидите:

график нагрузки (количество пользователей по времени)

среднее время ответа и p95/p99

сколько запросов было успешными, а сколько с ошибками

таблицу с порогами (должно быть зелёное «✓»)

Всё работает!

9. Останавливаем всё, когда наигрались
Остановить тест – достаточно закрыть терминал или нажать Ctrl+C.

Остановить сервисы:

В терминалах с bootRun нажмите Ctrl+C в каждом.

Остановить контейнеры (БД и Kafka):

bash
docker-compose down
10. Частые проблемы и как их решить
Проблема	Решение
docker-compose not recognized	Docker не установлен или не запущен. Запустите Docker Desktop.
port 5432 already in use	У вас локально запущен PostgreSQL. Остановите его или измените порт в docker-compose.yml.
./gradlew: Permission denied	На Linux/Mac дайте права: chmod +x gradlew
Kafka broker not available	Подождите ещё 30 секунд после docker-compose up, Kafka стартует дольше всех.
Тест показывает 100% ошибок	Проверьте, что оба сервиса запущены (порт 8080 и 8081 доступны). Зайдите в браузере на http://localhost:8080/satellites – должен быть пустой список [].
k6 не найдена команда	Перезапустите терминал после установки k6, или добавьте k6 в PATH вручную.
Отчёт не создался	Проверьте, что папка load-tests/reports существует. Создайте её вручную, если нужно.
11. Краткая шпаргалка (для тех, кто уже всё понял)
bash
# 1. Поднять инфраструктуру
docker-compose up -d

# 2. В терминале 1
cd space-operation-center && ./gradlew bootRun

# 3. В терминале 2
cd telemetry-service && ./gradlew bootRun

# 4. В терминале 3
cd load-tests
k6 run --out html=reports/report.html load-test.js

# 5. Смотреть отчёт: load-tests/reports/report.html

# 6. Закончить: Ctrl+C в терминалах, затем docker-compose down
12. Если ничего не работает
Проверьте версии:

bash
java -version          # должна быть 17
docker --version       # 20.10 или новее
k6 version             # 0.48 или новее
И убедитесь, что вы находитесь внутри папки satellite-constellation-system при выполнении команд.

Если ошибка осталась – скопируйте текст ошибки и отправьте тому, кто вам помогал настраивать. Удачи!
