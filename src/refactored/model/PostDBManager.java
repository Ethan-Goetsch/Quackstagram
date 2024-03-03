package refactored.model;
import java.io.Serializable;
import java.util.ArrayList;

import refactored.data.Paths;
import refactored.entities.Post;
import refactored.entities.UserAccount;

public class PostDBManager extends DBManager<Post>
{
    private static ArrayList<Post> posts;

    public static void main(String[] args)
    {
        storePosts();
    }

    public static void storePosts() { store(posts, Paths.postsPath); }
    public static void retrievePosts() { posts = retrieve(Paths.postsPath); }
}

