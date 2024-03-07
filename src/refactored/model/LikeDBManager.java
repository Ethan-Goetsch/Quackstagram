package refactored.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import refactored.entities.interactions.Like;
import refactored.entities.interactions.LikeType;
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
    
    public static void retrieveLikes() { likes = retrieve(Paths.likesDBPath); }
    public static void storeLikes() { store(likes, Paths.likesDBPath); }
}
