package refactored.entities.interactions;

import java.time.LocalDateTime;

import refactored.entities.Post;
import refactored.entities.UserAccount;

public class Like extends Notification{
    private int id;

    private UserAccount user;
    private Post post;
    
    private LocalDateTime timestamp;
}
