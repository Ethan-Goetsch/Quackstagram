package refactored.entities.interactions;

import java.time.LocalDateTime;

public class Like implements Notification{
    private int id;

    private int userID;
    private int postID;

    private LikeType type;
    
    private LocalDateTime timestamp;

    public Like(int id, int userID, int postID, LikeType type, LocalDateTime timestamp)
    {
        this.id = id;
        this.userID = userID;
        this.postID = postID;
        this.type = type;
        this.timestamp = timestamp;
    }

    public int getID() { return id; }
    public int getUser() { return userID; }
    public int getPost() { return postID; }
    public LikeType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getSenderID() { return userID; }
    public int getReceiverID() { return postID; }
    public String getNotificationMessage() { return "liked your post"; }
}