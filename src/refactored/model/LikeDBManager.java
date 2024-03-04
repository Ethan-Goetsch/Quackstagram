package refactored.model;

import java.util.ArrayList;

import refactored.entities.interactions.Like;
import refactored.factories.Paths;

public class LikeDBManager extends DBManager<Like>{
    private static ArrayList<Like> likes;

    public static void main(String[] args)
    {
        storeLikes();
    }
    
    public static void retrieveLikes() { likes = retrieve(Paths.likesPath); }
    public static void storeLikes() { store(likes, Paths.likesPath); }
}
