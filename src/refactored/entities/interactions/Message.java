package refactored.entities.interactions;

import java.io.Serializable;
import java.time.LocalDateTime;

import refactored.model.UserDBManager;

public class Message implements Serializable, Notification{
    private int id;

    private int senderID;
    private int receiverID;

    private String message;

    private LocalDateTime timestamp;

    public Message(int id, int senderID, int receiverID, String message, LocalDateTime timestamp)
    {
        this.id = id;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getID() { return id; }
    public int getSenderID() { return senderID; }
    public int getReceiverID() { return receiverID; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getNotificationMessage() { return UserDBManager.getUsername(senderID) + " said " + "\"" + message + "\""; }
}
