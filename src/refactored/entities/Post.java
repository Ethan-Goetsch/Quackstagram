package refactored.entities;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;

import refactored.factories.Paths;

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
    public Post(int id, Post parentPost, int authorID, Content content, String text, LocalDateTime timestamp)
    {
        this.id = id;
        
        this.authorID = authorID;
        this.timestamp = timestamp;
        
        this.content = content;
        this.text = text;

        likeCount = 0;
        commentCount = 0;
    }
    
    public void like() {
        likeCount++;
    }

    public void unlike() {
        likeCount--;
    }

    public int getID() { return id; }
    public int getAuthorID() { return authorID; }
    public Content getContent() { return content; }
    public String getFileName() { return content.fileName(); }
    public Path getFilePath() { return Path.of(Paths.uploadsPath.toString(), content.fileName()); }
    public String getText() { return text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getLikeCount() { return likeCount; }
    public int getCommentCount() { return commentCount; }

}