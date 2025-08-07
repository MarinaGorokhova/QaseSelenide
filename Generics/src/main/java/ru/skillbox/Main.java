package ru.skillbox;

import ru.skillbox.notification.EmailNotification;
import ru.skillbox.notification.PushNotification;
import ru.skillbox.notification.SmsNotification;
import ru.skillbox.notification_sender.EmailNotificationSender;
import ru.skillbox.notification_sender.PushNotificationSender;
import ru.skillbox.notification_sender.SmsNotificationSender;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EmailNotification email1 = new EmailNotification(
                "Успешная регистрация!",
                Arrays.asList("oleg@java.skillbox.ru", "masha@java.skillbox.ru", "yan@java.skillbox.ru"),
                "Спасибо за регистрацию на сервисе!");

        EmailNotification email2 = new EmailNotification(
                "Восстановление пароля",
                Arrays.asList("ivan@java.skillbox.ru"),
                "Ваш пароль был успешно изменён.");

        EmailNotification email3 = new EmailNotification(
                "Новости сервиса",
                Arrays.asList("user1@java.skillbox.ru", "user2@java.skillbox.ru"),
                "У нас новые функции! Проверьте их.");

        SmsNotification sms1 = new SmsNotification(
                Arrays.asList("+70001234567"),
                "Спасибо за регистрацию на сервисе!");

        SmsNotification sms2 = new SmsNotification(
                Arrays.asList("+70007654321", "+70009876543"),
                "Ваш код подтверждения: 123456");

        SmsNotification sms3 = new SmsNotification(
                Arrays.asList("+70001112233"),
                "Акция! Скидки до 50% на все товары!");

        PushNotification push1 = new PushNotification(
                "Успешная регистрация!",
                "o.yanovich",
                "Спасибо за регистрацию на сервисе!");

        PushNotification push2 = new PushNotification(
                "Восстановление пароля",
                "Ivanov",
                "Пароль изменён успешно.");

        PushNotification push3 = new PushNotification(
                "Новости",
                "Masha",
                "Появились новые возможности!");

        // Создаем сервисы отправки
        EmailNotificationSender emailSender = new EmailNotificationSender();
        SmsNotificationSender smsSender = new SmsNotificationSender();
        PushNotificationSender pushSender = new PushNotificationSender();

        // Отправка по одному уведомлению
        emailSender.send(email1);
        smsSender.send(sms1);
        pushSender.send(push1);

        // Отправка списков уведомлений
        emailSender.send(Arrays.asList(email2, email3));
        smsSender.send(Arrays.asList(sms2, sms3));
        pushSender.send(Arrays.asList(push2, push3));
    }
}
