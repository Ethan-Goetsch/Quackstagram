package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.User;

public class Follow implements Notification {
    private int id;
    
    private User follower;
    private User followee;

    private FollowType type;

    private LocalDateTime timestamp;
}


