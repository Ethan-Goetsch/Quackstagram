package refactored.model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import refactored.entities.Content;
import refactored.entities.Post;
import refactored.entities.User;
import refactored.factories.Paths;

public class PostDBManager extends DBManager<Post>
{
    private static ArrayList<Post> posts;

    public static void main(String[] args)
    {
        // init
        posts = new ArrayList<>();
        posts.add(new Post(null, 1, new Content(1, "Lorin_1.png"), "In the cookie jar my hand was not.", LocalDateTime.of(2023, 12, 17, 19, 07, 43)));
        posts.add(new Post(null, 2, new Content(2, "Lorin_2.png"), "Meditate I must.", LocalDateTime.of(2023,12,17,19,9,35)));
        posts.add(new Post(null, 3, new Content(3, "Xylo_1.png"), "My tea strong as Force is.", LocalDateTime.of(2023,12,17,19,22,40)));
        posts.add(new Post(null, 4, new Content(4, "Xylo_2.png"), "Jedi mind trick failed.", LocalDateTime.of(2023,12,17,19,23,14)));
        posts.add(new Post(null, 5, new Content(5, "Zara_1.png"), "Lost my map I have. Oops.", LocalDateTime.of(2023,12,17,19,24,31)));
        posts.add(new Post(null, 6, new Content(6, "Zara_2.png"), "Yoga with Yoda", LocalDateTime.of(2023,12,17,19,25,03)));
        posts.add(new Post(null, 7, new Content(7, "Mystar_1.png"), "Cookies gone?", LocalDateTime.of(2023,12,17,19,26,50)));
        posts.add(new Post(null, 8, new Content(8, "Mystar_2.png"), "In my soup a fly is.", LocalDateTime.of(2023,12,17,19,27,24)));
        storePosts();

        // test
        posts = null;
        retrievePosts();
        System.out.println(posts.get(7).getTimestamp());
    }

    public static void createPost() 
    { 
        //TODO: implement
        store(posts, Paths.postsPath);
    }

    public static int getPostCount(int userID)
    {
        retrievePosts();
        int count = 0;
        for(Post p : posts)
        {
            if(p.getAuthorID() == userID)
            {
                count++;
            }
        }
        return count;
    }

    public static void retrievePosts() {
        if (posts == null)
            posts = retrieve(Paths.postsPath);
    }
    public static void storePosts() { store(posts, Paths.postsPath); }
}

