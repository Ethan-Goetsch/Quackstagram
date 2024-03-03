package refactored;

import java.util.List;

import refactored.stored.Post;

// Represents a user on Quackstagram
public class User
{
    private final int id;

    private final List<Post> posts;
    private final List<User> followers;
    private final List<User> followings;

    private String username;
    private String password;
    private String bio;

    public User(int id, String username, String password, String bio, List<Post> posts, List<User> followers, List<User> followings)
    {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.password = password;
        this.posts = posts;
        this.followers = followers;
        this.followings = followings;
    }

    public int getID() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getBio() { return bio; }
    public List<Post> getPosts() { return posts; }
    public List<User> getFollowers() { return followers; }
    public List<User> getFollowings() { return followings; }

    @Override
    public String toString()
    {
        return "[User]: " + "Username: " + username;
    }
}