package refactored.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import refactored.Paths;
import refactored.entities.Post;
import refactored.entities.User;

public class UserDBManager extends DBManager<User>
{
    private static ArrayList<User> users;
    private static int currentID = -1;

    public static void main(String[] args)
    {
        // init
        users = new ArrayList<>();
        users.add(new User(generateID(), "Lorin","Password","For copyright reasons, I am not Grogu"));
        users.add(new User(generateID(), "Xylo","Password","Fierce warrior, not solo"));
        users.add(new User(generateID(), "Zara","Password","Humanoid robot much like the rest"));
        users.add(new User(generateID(), "Mystar","Password","Xylo and I are not the same!"));
        storeUsers();


        // test
        users = null;
        retrieveUsers();
        print();
    }

    private static void print() 
    {
        for(User u : users)
        {
            System.out.println(u.getId() + " " + u.getUsername() + " " + u.getBio());
        }
    }

    public static int getUserId(String username, String password)
    {
        if (currentID != -1)
        {
            return currentID;
        }

        Optional<User> foundUser = users.stream()
        .filter(user -> user.getUsername() == username && user.getPassword() == password)
        .findFirst();

        return foundUser.isPresent() ? foundUser.get().getId() : -1;
    }

    public static String getBio(int id)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == id)
            {
                return u.getBio();
            }
        }
        return null;
    }

    public static String getUsername (int id)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == id)
            {
                return u.getUsername();
            }
        }
        return null;
    }

    /**
     * Validates credentials and returns the user id if they are correct;
     * returns -1 if they are not.
     * @param username
     * @param password
     * @return
     */
    public static boolean verifyCredentials(String username, String password)
    {
        retrieveUsers();

        //credentials validation logic
        for(User uc : users)
        {
            if(uc.getUsername().equals(username) && uc.getPassword().equals(password))
            {
                currentID =  uc.getId();
                return true;
            }
        }
        return false;
    }

    /**
     * If the username doesn't already exist, creates a new user and adds it to the database.
     * Returns a boolean response to indicate success or failure.
     * @param username
     * @param password
     * @return
     */
    public static boolean createUser(String username, String password, String bio)
    {
        retrieveUsers();
        if(!usernameExists(username))
        {
            User user = new User(generateID(), username, password, bio);
            users.add(user);
            storeUsers();
            return true;
        }
        return false;
    }
    
    public static boolean usernameExists(String username)
    {
        retrieveUsers();
        //username validation logic
        for(User ua : users)
        {
            if(ua.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    private static int generateID()
    {
        retrieveUsers();
        int max = 0;
        for(User ua : users)
        {
            if(ua.getId() > max)
            {
                max = ua.getId();
            }
        }
        return ++max;
    }

    public static String getAuthorUsername(Post post)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == post.getAuthorID())
            {
                return u.getUsername();
            }
        }
        return null;
    }

    public static ArrayList<User> getUserFollowees(int userID)
    {
        retrieveUsers();
        ArrayList<User> followees = new ArrayList<>();
        for(User u : users)
        {
            //it's impossible for a user to follow himself //maybe in the future it this may change
            if(FollowDBManager.isAFollowingB(userID, u.getId()))
            {
                followees.add(u);
            }
        }
        return followees;
    }

    //return the Paths to the content of the post in an Iterator
    public static class UserFollowees implements Iterable<Integer>
    {
        private int userID;
        private ArrayList<User> userFollowees;
        public UserFollowees(int userID)
        {
            this.userID = userID;
            retrieveUsers();
            userFollowees = getUserFollowees(userID);
        }
        
        public class UserFolloweeIDsIterator implements Iterator<Integer>
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < userFollowees.size();
            }

            @Override
            public Integer next()
            {
                return userFollowees.get(index++).getId();
            }
        }

        @Override
        public Iterator<Integer> iterator()
        {
            return new UserFollowees.UserFolloweeIDsIterator();
        }
    }

    private static void retrieveUsers()
    {
        if(users == null)
            users = retrieve(Paths.usersDBPath);
    }
    private static void storeUsers() { store(users, Paths.usersDBPath); }

    public static String getAuthorBio(Post post)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == post.getAuthorID())
            {
                return u.getBio();
            }
        }
        return null;
    }

    public static void userAFollowedB(int followerID, int followeeID)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == followeeID)
            {
                u.incrementFollowers();
            }
            if (u.getId() == followerID)
            {
                u.incrementFollowing();
            }
        }
        storeUsers();
    }

    public static void userAUnfollowedB(int followerID, int followeeID)
    {
        retrieveUsers();
        for(User u : users)
        {
            if(u.getId() == followeeID)
            {
                u.decrementFollowers();
            }
            if (u.getId() == followerID)
            {
                u.decrementFollowing();
            }
        }
        storeUsers();
    }
}
