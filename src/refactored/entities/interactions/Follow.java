package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.UserAccount;

public class Follow implements Notification {
    private int id;
    
    private UserAccount follower;
    private UserAccount followee;

    private FollowType type;

    private LocalDateTime timestamp;
}


