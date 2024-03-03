package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.UserAccount;

public class Message extends Notification{
    private int id;

    private UserAccount sender;
    private UserAccount receiver;

    private String message;

    private LocalDateTime timestamp;
}
