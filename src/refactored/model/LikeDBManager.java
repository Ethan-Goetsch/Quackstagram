package refactored.model;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;

import refactored.entities.Post;
import refactored.entities.interactions.Like;
import refactored.entities.interactions.LikeType;
import refactored.entities.interactions.Notification;
import refactored.factories.Paths;

public class LikeDBManager extends DBManager<Like>{
    private static ArrayList<Like> likes;

    public static void main(String[] args)
    {
        storeLikes();
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
        private ArrayList<Like> likes;

        public UserReceivedLikes(int userID)
        {
            retrieveLikes();

            this.userID = userID;
            this.userPosts = PostDBManager.getUserPosts(userID);

            this.likes = new ArrayList<Like>();
            for(Like l : likes)
            {
                for(Post p : userPosts)
                    if(l.getUser() == userID && l.getPost() == p.getID())
                    {
                        likes.add(l);
                    }
            }
        }

        public int getUserID() { return userID; }
        public ArrayList<Post> getUserPosts() { return userPosts; }

        @Override
        public java.util.Iterator<Like> iterator() {
            return likes.iterator();
        }
    }
    
    public static void retrieveLikes() { likes = retrieve(Paths.likesDBPath); }
    public static void storeLikes() { store(likes, Paths.likesDBPath); }
}
