package refactored.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import refactored.entities.Post;
import refactored.entities.interactions.Like;
import refactored.entities.interactions.LikeType;
import refactored.factories.Paths;

public class LikeDBManager extends DBManager<Like>{
    private static ArrayList<Like> likes;

    public static void main(String[] args)
    {
        // init
        // likes = new ArrayList<>();
        // likes.add(new Like(1, 1, 1, LikeType.LIKE, LocalDateTime.of(2023, 12, 17, 19, 07, 43)));
        // storeLikes();

        // test
        likes = null;
        retrieveLikes();
        print();
    }

    public static void print() {
        for(Like l : likes)
        {
            System.out.println(l.getUserID() + " " + l.getPostID());
        }
    }

    public static void createLike(int userID, int postID, LikeType type)
    {
        retrieveLikes();
        likes.add(new Like(generateID(), userID, postID, type, LocalDateTime.now()));
        PostDBManager.likePost(postID);
        storeLikes();
    }

    private static int generateID()
    {
        retrieveLikes();
        int max = 0;
        for(Like l : likes)
        {
            if(l.getID() > max)
            {
                max = l.getID();
            }
        }
        return ++max;
    }

    public static class UserReceivedLikes implements Iterable<Like>
    {
        private int userID;
        private ArrayList<Post> userPosts;
        private ArrayList<Like> userLikes;

        public UserReceivedLikes(int userID)
        {
            retrieveLikes();
            print();

            this.userID = userID;
            this.userPosts = PostDBManager.getUserPosts(userID);

            this.userLikes = new ArrayList<Like>();
            for(Like l : likes)
            {
                for(Post p : userPosts)
                    if(l.getPostID() == p.getID())
                    {
                        userLikes.add(l);
                    }
            }
        }

        public int getUserID() { return userID; }
        public ArrayList<Post> getUserPosts() { return userPosts; }

        @Override
        public java.util.Iterator<Like> iterator() {
            return userLikes.iterator();
        }
    }
    
    public static void retrieveLikes() {
        if(likes == null)
            likes = retrieve(Paths.likesDBPath); }
    public static void storeLikes() { store(likes, Paths.likesDBPath); }
}
