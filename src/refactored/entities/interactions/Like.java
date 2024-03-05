package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.Post;
import refactored.entities.User;

public class Like implements Notification{
    private int id;

    private User user;
    private Post post;

    private LikeType type;
    
    private LocalDateTime timestamp;
}
