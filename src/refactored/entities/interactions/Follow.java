package refactored.entities.interactions;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Follow implements Notification, Serializable {
    private int id;
    
    private int followerID;
    private int followeeID;

    private FollowType type;

    private LocalDateTime timestamp;

    public Follow(int followerID, int followeeID, FollowType type, LocalDateTime timestamp)
    {
        this.id = 0; // TODO: 12 years ago: generate a unique id
        
        this.followerID = followerID;
        this.followeeID = followeeID;
        this.type = type;
        this.timestamp = timestamp;
    }

    public int getID() { return id; }
    public int getFollowerID() { return followerID; }
    public int getFolloweeID() { return followeeID; }
    public FollowType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getSenderID() { return followerID; }
    public int getReceiverID() { return followeeID; }
    public String getNotificationMessage() { return "followed you"; }
}


