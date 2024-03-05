package refactored.model;

import java.util.ArrayList;

import refactored.entities.User;
import refactored.factories.Paths;

public class UserDBManager extends DBManager<User>
{
    private static ArrayList<User> users;
    private static int currentID;

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
        System.out.println(users.get(0).username);
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
            if(uc.username.equals(username) && uc.password.equals(password))
            {
                currentID = uc.id;
                return true;
            }
        }
        return false;
    }

    public static boolean usernameExists(String username) {
        //username validation logic
        for(User ua : users)
        {
            if(ua.username.equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public static void createUser(String username, String password) {
        retrieveUsers();
        if(!usernameExists(username))
        {
            User user = new User(generateID(), username, password);
            users.add(user);
            storeUsers();
        }
        storeUsers();
    }

    private static int generateID()
    {
        retrieveUsers();
        for(User ua : users)
        {
            if(ua.id > currentID)
            {
                currentID = ua.id;
            }
        }
        return ++currentID;
    }

    public void deleteUser(int id) {
        for(User ua : users)
        {
            if(ua.id == id)
            {
                users.remove(ua);
                storeUsers();
                return;
            }
        }
    }

    /**
     * Updates the user with the same id as the given user.
     * // Might want to replace with something that changes a specific field for an id.
     * @param user
     */
    public static void updateUser(User user) {
        for(User ua : users)
        {
            if(ua.id == user.id)
            {
                ua = user;
                storeUsers();
                return;
            }
        }
    }

    private static void retrieveUsers()
    {
        if(users == null)
            users = retrieve(Paths.usersPath);
    }

    private static void storeUsers()
    {
        store(users, Paths.usersPath);
    }
}
