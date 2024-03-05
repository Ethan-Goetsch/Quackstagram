package refactored.entities;

import java.io.Serializable;

public class UserAccount implements Serializable
{
    public int id;
    public String username;
    public String password;

    public String bio;
    public Content profilePicture; // should be more specific, maybe a constrained subclass -> would violate Liskov Substitution Principle?

    // stored with the user for efficiency. Otherwise, a full search would be required each page update.
    public int postsCount;
    public int followersCount;
    public int followingCount;
}