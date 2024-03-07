package refactored.model;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import refactored.entities.Content;
import refactored.entities.Post;
import refactored.entities.User;
import refactored.factories.Paths;

public class PostDBManager extends DBManager<Post> implements Iterable<Path>
{
    private static ArrayList<Post> posts;

    public static void main(String[] args)
    {
        // init
        posts = new ArrayList<>();
        posts.add(new Post(1, null, 1, new Content(1, "Lorin_1.png"), "In the cookie jar my hand was not.", LocalDateTime.of(2023, 12, 17, 19, 07, 43)));
        posts.add(new Post(2, null, 1, new Content(2, "Lorin_2.png"), "Meditate I must.", LocalDateTime.of(2023,12,17,19,9,35)));
        posts.add(new Post(3, null, 2, new Content(3, "Xylo_1.png"), "My tea strong as Force is.", LocalDateTime.of(2023,12,17,19,22,40)));
        posts.add(new Post(4, null, 2, new Content(4, "Xylo_2.png"), "Jedi mind trick failed.", LocalDateTime.of(2023,12,17,19,23,14)));
        posts.add(new Post(5, null, 3, new Content(5, "Zara_1.png"), "Lost my map I have. Oops.", LocalDateTime.of(2023,12,17,19,24,31)));
        posts.add(new Post(6, null, 3, new Content(6, "Zara_2.png"), "Yoga with Yoda", LocalDateTime.of(2023,12,17,19,25,03)));
        posts.add(new Post(7, null, 4, new Content(7, "Mystar_1.png"), "Cookies gone?", LocalDateTime.of(2023,12,17,19,26,50)));
        posts.add(new Post(8, null, 4, new Content(8, "Mystar_2.png"), "In my soup a fly is.", LocalDateTime.of(2023,12,17,19,27,24)));
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
        posts.add(new Post(generateID(), null, authorID, new Content(getnerateContentID(), imageFileName), text, LocalDateTime.now()));
        storePosts();
    }

    private static int generateID()
    {
        retrievePosts();
        int max = 0;
        for(Post p : posts)
        {
            if(p.getID() > max)
            {
                max = p.getID();
            }
        }
        return ++max;
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
    public static class UserFolloweePosts implements Iterable<Post>
    {
        private int userID;
        private ArrayList<User> userFollowees;
        private ArrayList<Post> userFolloweePosts;
        
        public UserFolloweePosts(int userID)
        {
            retrievePosts();
            this.userID = userID;
            userFollowees = UserDBManager.getUserFollowees(userID);
            // get posts of followees // uses the fact that posts are ordered chronologically to avoid sorting the list
            for (Post p : posts)
            {
                for(User u : userFollowees)
                {
                    if(p.getAuthorID() == u.getId())
                    {
                        userFolloweePosts.add(p);
                    }
                }
            }
        }

        /**
         * // unused
         * Returns the Paths of the Posts of the Users that a given User follows;
         * in most recent to last order.
         */
        public class UserFolloweePostPathsIterator implements Iterator<Path>
        {
            private int index = userFolloweePosts.size() - 1;

            @Override
            public boolean hasNext()
            {
                return index >= 0;
            }

            @Override
            public Path next()
            {
                return Path.of(Paths.uploadsPath.toString(), posts.get(index--).getContent().fileName());
            }
        }

        public class UserFolloweePostsIterator implements Iterator<Post>
        {
            private int index = userFolloweePosts.size() - 1;

            @Override
            public boolean hasNext()
            {
                return index >= 0;
            }

            @Override
            public Post next()
            {
                return userFolloweePosts.get(index--);
            }
        }

        @Override
        public Iterator<Post> iterator()
        {
            return new UserFolloweePosts.UserFolloweePostsIterator();
        }
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
        
        public class UserPostPathsIterator implements Iterator<Path>
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
            return new UserPostPathsIterator();
        }
    }

    public static class PostPathsIterator implements Iterator<Path>
    {
        private int index = 0;

        @Override
        public boolean hasNext()
        {
            retrievePosts();
            return index < posts.size();
        }

        @Override
        public Path next()
        {
            retrievePosts();
            return Path.of(Paths.uploadsPath.toString(), posts.get(index++).getContent().fileName());
        }
    }

    @Override
    public Iterator<Path> iterator()
    {
        return new PostPathsIterator();
    }

    public static void retrievePosts() {
        if (posts == null)
            posts = retrieve(Paths.postsDBPath);
    }
    public static void storePosts() { store(posts, Paths.postsDBPath); }
}

