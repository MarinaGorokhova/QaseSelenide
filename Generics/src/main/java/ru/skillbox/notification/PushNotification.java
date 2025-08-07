package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushNotification implements Notification {
    private String title;
    private String receiver;
    private String message;

//    @Override
//    public String formattedMessage() {
//        return "";
//    }

    @Override
    public String formatMessage() {
        return "\ud83d\udc4b " + message;
    }
}

