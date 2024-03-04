package refactored.model;

import java.util.ArrayList;

import refactored.entities.interactions.Follow;
import refactored.factories.Paths;

public class FollowDBManager extends DBManager<Follow>
{
    private static ArrayList<Follow> follows;

    public static void main(String[] args)
    {
        storeFollows();
    }

    public static void storeFollows() { store(follows, Paths.followsPath); }
    public static void retrieveFollows() { follows = retrieve(Paths.followsPath); }
}
