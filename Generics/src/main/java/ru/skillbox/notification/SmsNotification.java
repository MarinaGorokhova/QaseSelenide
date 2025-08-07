package ru.skillbox.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SmsNotification implements Notification {
    private List<String> receivers;
    private String message;

//    @Override
//    public String formattedMessage() {
//        return "";
//    }

    @Override
    public String formatMessage() {
        return message;
    }
}