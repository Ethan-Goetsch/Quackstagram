package refactored.model;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

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
        posts.add(new Post(null, 1, new Content(2, "Lorin_2.png"), "Meditate I must.", LocalDateTime.of(2023,12,17,19,9,35)));
        posts.add(new Post(null, 2, new Content(3, "Xylo_1.png"), "My tea strong as Force is.", LocalDateTime.of(2023,12,17,19,22,40)));
        posts.add(new Post(null, 2, new Content(4, "Xylo_2.png"), "Jedi mind trick failed.", LocalDateTime.of(2023,12,17,19,23,14)));
        posts.add(new Post(null, 3, new Content(5, "Zara_1.png"), "Lost my map I have. Oops.", LocalDateTime.of(2023,12,17,19,24,31)));
        posts.add(new Post(null, 3, new Content(6, "Zara_2.png"), "Yoga with Yoda", LocalDateTime.of(2023,12,17,19,25,03)));
        posts.add(new Post(null, 4, new Content(7, "Mystar_1.png"), "Cookies gone?", LocalDateTime.of(2023,12,17,19,26,50)));
        posts.add(new Post(null, 4, new Content(8, "Mystar_2.png"), "In my soup a fly is.", LocalDateTime.of(2023,12,17,19,27,24)));
        storePosts();

        // test
        posts = null;
        retrievePosts();
        System.out.println(posts.get(7).getTimestamp());
    }

    public static int getnerateContentID()
    {
        retrievePosts();
        int max = 0;
        for(Post p : posts)
        {
            if(p.getContent().id() > max)
            {
                max = p.getContent().id();
            }
        }
        return ++max;
    }

    public static ArrayList<Post> getUserPosts(int userID)
    {
        retrievePosts();
        ArrayList<Post> userPosts = new ArrayList<>();
        for(Post p : posts)
        {
            if(p.getAuthorID() == userID)
            {
                userPosts.add(p);
            }
        }
        return userPosts;
    }

    public static void createPost(int authorID, String imageFileName, String text)
    {
        retrievePosts();
        posts.add(new Post(null, authorID, new Content(getnerateContentID(), imageFileName), text, LocalDateTime.now()));
        storePosts();
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

    //return the Paths to the content of the post in an Iterator
    public static class UserPosts implements Iterable<Path>
    {
        private int userID;
        private ArrayList<Post> userPosts;
        public UserPosts(int userID)
        {
            this.userID = userID;
            retrievePosts();
            posts = getUserPosts(userID);
        }
        
        public class UserPostsIterator implements Iterator<Path>
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < posts.size();
            }

            @Override
            public Path next()
            {
                return Path.of(Paths.uploadsPath.toString(), posts.get(index++).getContent().fileName());
            }
        }

        @Override
        public Iterator<Path> iterator()
        {
            return new UserPostsIterator();
        }
    }

    public static void retrievePosts() {
        if (posts == null)
            posts = retrieve(Paths.postsDBPath);
    }
    public static void storePosts() { store(posts, Paths.postsDBPath); }
}

