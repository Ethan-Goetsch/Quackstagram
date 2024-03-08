package refactored.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import refactored.entities.interactions.Follow;
import refactored.entities.interactions.FollowType;
import refactored.factories.Paths;

public class FollowDBManager extends DBManager<Follow>
{
    private static ArrayList<Follow> follows;

    public static void main(String[] args)
    {
        // init
        // follows = new ArrayList<>();
        // follows.add(new Follow(1, 4, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(1, 3, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(2, 1, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(2, 3, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(3, 1, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(4, 1, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // follows.add(new Follow(4, 3, FollowType.FOLLOW, LocalDateTime.of(1999, 12, 31, 23, 59, 59)));
        // storeFollows();

        // test
        follows = null;
        retrieveFollows();
        print();
    }

    private static void print()
    {
        for(Follow f : follows)
        {
            System.out.println(f.getFollowerID() + f.getType().toString() + f.getFolloweeID());
        }
    }

    public static void createFollow(int followerID, int followeeID)
    {
        retrieveFollows();
        if (!isAFollowingB(followerID, followeeID))
        {
            follows.add(new Follow(followerID, followeeID, FollowType.FOLLOW, LocalDateTime.now()));
            UserDBManager.userAFollowedB(followerID, followeeID);
        } else
        {
            follows.add(new Follow(followerID, followeeID, FollowType.UNFOLLOW, LocalDateTime.now()));
            UserDBManager.userAUnfollowedB(followerID, followeeID);
        }
        storeFollows();
    }

    /**
     * Returns whether user A is following user B, given their IDs. It is based on the follows list being order chronologically, with the most recent follows at the end.
     * In order to be more efficient and avoid iterating through the entire list of follows, it iterates backwards, returning the first match, or false if none is found.
     * @param followerID
     * @return
     */
    public static boolean isAFollowingB(int userAID, int userBID)
    {
        retrieveFollows();
        for(int i = follows.size() - 1; i >= 0; i--) // iterate backwards to find the most recent follow
        {
            Follow f = follows.get(i);
            if(f.getFollowerID() == userAID && f.getFolloweeID() == userBID)
                if (f.getType() == FollowType.FOLLOW)
                {
                    return true;
                } else
                {
                    return false;
                }
        }
        return false;
    }

    public static int getFollowerCount(int followerID)
    {
        retrieveFollows();
        int count = 0;
        for(Follow f : follows)
        {
            if(f.getFollowerID() == followerID)
            {
                if (f.getType() == FollowType.FOLLOW)
                    count++;
                else
                    count--;
            }
        }
        return count;
    }

    public static int getFolloweeCount(int followeeID)
    {
        retrieveFollows();
        int count = 0;
        for(Follow f : follows)
        {
            if(f.getFolloweeID() == followeeID)
            {
                if (f.getType() == FollowType.FOLLOW)
                    count++;
                else
                    count--;
            }
        }
        return count;
    }

    public static class UserFollowings implements Iterable<Follow>
    {
        private int userID;
        private ArrayList<Follow> userFollowings;

        public UserFollowings(int userID)
        {
            retrieveFollows();
            this.userID = userID;
            userFollowings = new ArrayList<>();
            for(Follow f : follows)
            {
                if(f.getFollowerID() == userID)
                {
                    userFollowings.add(f);
                }
            }
        }

        @Override
        public Iterator<Follow> iterator()
        {
            return userFollowings.iterator();
        }
    }

    public static class UserReceivedFollows implements Iterable<Follow>
    {
        private int userID;
        private ArrayList<Follow> userReceivedFollows;

        public UserReceivedFollows(int userID)
        {
            retrieveFollows();
            this.userID = userID;
            userReceivedFollows = new ArrayList<>();
            for(Follow f : follows)
            {
                if(f.getFolloweeID() == userID)
                {
                    userReceivedFollows.add(f);
                }
            }
        }

        @Override
        public Iterator<Follow> iterator()
        {
            return userReceivedFollows.iterator();
        }
    }

    public static void retrieveFollows()
    {
        if(follows == null)
            follows = retrieve(Paths.followsDBPath);
    }
    public static void storeFollows() { store(follows, Paths.followsDBPath); }

}
