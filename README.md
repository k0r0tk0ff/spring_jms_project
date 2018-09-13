# spring_jms_project


Use:
https://spring.io/guides/gs/messaging-jms/
https://www.codenotfound.com/spring-jms-activemq-example.html
https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html

Задача
Реализовать приложение, которое разворачивает Embedded брокер,
а так запускает 2 потока:  отправитель и получатель.
Отправитель с некоторой периодичностью генерирует сообщения и отправляет брокер.
Получатель слушает очередь, и при появлении там сообщения
производит запись сообщения в БД через JDBC.
Запись производится в 2 таблицы, в одной тело сообщения, в другой - заголовки.
Связь между таблицами реализовать с помощью внешнего ключа.
Явно запускать потоки не следует.
Настройку, запуск, конфигурирование осуществлять при помощи средств Spring Framework.

Предполагаемое время решения 8-14 часов.
