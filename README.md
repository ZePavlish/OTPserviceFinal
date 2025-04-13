# OTP Service

Этот сервис предоставляет функциональность для генерации и отправки OTP-кодов через различные каналы связи, включая Email, SMPP и Telegram. Сервис также поддерживает сохранение OTP-кодов в файл и предоставляет токенную аутентификацию.

## Как пользоваться сервисом

Для использования сервиса выполните следующие шаги:

Установите зависимости с помощью Gradle:
./gradlew build

Запустите приложение:
./gradlew bootRun

Приложение будет доступно по адресу:
http://localhost:8080

Какие команды поддерживаются
Основные команды API:
POST /api/send-otp/email

Описание: Генерация OTP-кода и его отправка на Email.

Параметры:

email (String) — Email адрес получателя.

Ответ:

Статус 200 — OTP отправлен успешно.

Статус 400 — Ошибка отправки OTP.

POST /api/send-otp/sms

Описание: Генерация OTP-кода и его отправка через SMPP эмулятор.

Параметры:

phoneNumber (String) — Номер телефона получателя.

Ответ:

Статус 200 — OTP отправлен успешно.

Статус 400 — Ошибка отправки OTP.

POST /api/send-otp/telegram

Описание: Генерация OTP-кода и его отправка через Telegram.

Параметры:

telegramId (String) — ID пользователя в Telegram.

Ответ:

Статус 200 — OTP отправлен успешно.

Статус 400 — Ошибка отправки OTP.

GET /api/validate-otp

Описание: Проверка валидности OTP-кода.

Параметры:

otp (String) — OTP-код, который нужно проверить.

Ответ:

Статус 200 — Код валиден.

Статус 400 — Код невалиден.

## В репозитории проекта OTPserviceFinal используются следующие внешние библиотеки:

PostgreSQL — для работы с базой данных:

org.postgresql:postgresql:42.7.5

Spring Boot Web — для создания REST API:

org.springframework.boot:spring-boot-starter-web

Jackson Databind — для работы с JSON:

com.fasterxml.jackson.core:jackson-databind:2.15.2

JWT — для работы с токенами:

io.jsonwebtoken:jjwt-api:0.11.5

io.jsonwebtoken:jjwt-impl:0.11.5

io.jsonwebtoken:jjwt-jackson:0.11.5

JavaMail — для рассылки email:

com.sun.mail:javax.mail:1.6.2

Slf4j и Logback — для логирования:

org.slf4j:slf4j-api:2.0.7

ch.qos.logback:logback-classic:1.4.8

BCrypt — для шифрования паролей:

org.mindrot:jbcrypt:0.4
