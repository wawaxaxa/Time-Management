package com.example.springboot;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String title, String message) {
        try {
            String script = String.format(
                "display notification \"%s\" with title \"%s\"",
                message, title
            );
            ProcessBuilder pb = new ProcessBuilder("osascript", "-e", script);
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}