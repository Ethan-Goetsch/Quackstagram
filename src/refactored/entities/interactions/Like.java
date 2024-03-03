package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.Post;
import refactored.entities.UserAccount;

public class Like implements Notification{
    private int id;

    private UserAccount user;
    private Post post;

    private LikeType type;
    
    private LocalDateTime timestamp;
}
