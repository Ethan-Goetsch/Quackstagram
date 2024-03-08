package refactored.entities;

import java.io.Serializable;

public class User implements Serializable
{
    private int id;

    private String username;
    private String password;

    private String bio;
    private Content profilePicture; // should be more specific, maybe a constrained subclass -> would violate Liskov Substitution Principle?

    // stored with the user for efficiency. Otherwise, a full search would be required each page update.
    private int postsCount;
    private int followersCount;
    private int followingCount;

    public User(int id, String username, String password, String bio) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.bio = "";
    }

    public User(Builder builder)
    {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.bio = builder.bio;
        this.profilePicture = builder.profilePicture;
        this.postsCount = builder.postsCount;
        this.followersCount = builder.followersCount;
        this.followingCount = builder.followingCount;
    }

    public static class Builder
    {
        private int id;
        private String username;
        private String password;
        private String bio;
        private Content profilePicture;
        private int postsCount;
        private int followersCount;
        private int followingCount;

        public Builder(int id, String username, String password)
        {
            this.id = id;
            this.username = username;
            this.password = password;
        }
        
        public Builder bio(String bio) { this.bio = bio; return this; }
        public Builder profilePicture(Content profilePicture) { this.profilePicture = profilePicture; return this; }
        public Builder postsCount(int postsCount) { this.postsCount = postsCount; return this; }
        public Builder followersCount(int followersCount) { this.followersCount = followersCount; return this; }
        public Builder followingCount(int followingCount) { this.followingCount = followingCount; return this; }
        
        public User build()
        {
            return new User(this);
        }
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getBio() { return bio; }
    public Content getProfilePicture() { return profilePicture; }
    public int getPostsCount() { return postsCount; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }

    public void incrementFollowers() {
        followersCount++;
    }

    public void decrementFollowers() {
        followersCount--;
    }

    public void incrementFollowing() {
        followingCount++;
    }

    public void decrementFollowing() {
        followingCount--;
    }
}