package refactored.stored;
import java.io.Serializable;

import Picture;

public class UserAccount implements Serializable
{
    public int id;
    public String username;
    public String bio;
    public Picture profilePicture;
    public int postsCount;
    public int followersCount;
    public int followingCount;
}