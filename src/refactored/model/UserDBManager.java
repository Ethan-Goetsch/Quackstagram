package refactored.model;

import java.util.ArrayList;

import refactored.entities.User;
import refactored.factories.Paths;

public class UserDBManager extends DBManager<User>
{
    private static ArrayList<User> users;
    public static int currentID;

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
        System.out.println(users.get(0).getUsername());
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
                currentID = uc.getId();
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
    public static boolean createUser(String username, String password) {
        retrieveUsers();
        if(!usernameExists(username))
        {
            User user = new User(generateID(), username, password);
            users.add(user);
            storeUsers();
            return true;
        }
        return false;
    }
    
    public static boolean usernameExists(String username) {
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
        for(User ua : users)
        {
            if(ua.getId() > currentID)
            {
                currentID = ua.getId();
            }
        }
        return ++currentID;
    }

    private static void retrieveUsers()
    {
        if(users == null)
            users = retrieve(Paths.usersPath);
    }
    private static void storeUsers() { store(users, Paths.usersPath); }
}
