package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.User;

public class Message implements Notification{
    private int id;

    private User sender;
    private User receiver;

    private String message;

    private LocalDateTime timestamp;
}
