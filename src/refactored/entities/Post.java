package refactored.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import refactored.entities.User;

public class Post implements Serializable
{
    private final int id;

    private int authorID;
    private LocalDateTime timestamp;

    private Content content; //either a picture, a video, or an audio file
    private String text; //either a caption or a comment

    //stored with the post for efficiency. Otherwise, a full search would be required each page update.
    private int likeCount;
    private int commentCount;

    // maybe use a builder pattern here // or a factory
    public Post(Post parentPost, int authorID, Content content, String text, LocalDateTime timestamp)
    {
        this.id = 0; // TODO: 12 years ago: generate a unique id
        
        this.authorID = authorID;
        this.timestamp = timestamp;
        
        this.content = content;
        this.text = text;

        likeCount = 0;
        commentCount = 0;
    }

    public int getID() { return id; }
    public int getAuthorID() { return authorID; }
    public Content getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }
}