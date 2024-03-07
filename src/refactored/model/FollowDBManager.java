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
        follows = new ArrayList<>();
        follows.add(new Follow(1, 4, FollowType.FOLLOW, null));
        follows.add(new Follow(1, 3, FollowType.FOLLOW, null));
        follows.add(new Follow(2, 1, FollowType.FOLLOW, null));
        follows.add(new Follow(2, 3, FollowType.FOLLOW, null));
        follows.add(new Follow(3, 1, FollowType.FOLLOW, null));
        follows.add(new Follow(4, 1, FollowType.FOLLOW, null));
        follows.add(new Follow(4, 3, FollowType.FOLLOW, null));
        storeFollows();

        // test
        follows = null;
        retrieveFollows();
        System.out.println(follows.get(6).getFollowerID());
    }

    public static void createFollow(int followerID, int followeeID, FollowType type)
    {
        retrieveFollows();
        follows.add(new Follow(followerID, followeeID, type, LocalDateTime.now()));
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
                count++;
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
                count++;
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
