package model;
import java.io.Serializable;

import Picture;

public class UserAccount implements Serializable
{
    int id;
    String username;
    String bio;
    Picture profilePicture;
    int postsCount;
    int followersCount;
    int followingCount;
}