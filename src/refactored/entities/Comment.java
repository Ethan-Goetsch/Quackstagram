package refactored.entities;

import java.time.LocalDateTime;

import refactored.entities.User;

public class Comment extends Post{
    private final Post parent; //the post that this post is a comment on. Null if this post is not a comment.
    
    public Comment(Post parentPost, User author, Content content, String text, LocalDateTime timestamp)
    {
        super(parentPost, author, content, text, timestamp);
        this.parent = parentPost;
    }
}
