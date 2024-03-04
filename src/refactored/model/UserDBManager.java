package refactored.model;

import java.util.ArrayList;

import refactored.entities.UserAccount;
import refactored.factories.Paths;

public class UserDBManager extends DBManager<UserAccount>
{
    private static ArrayList<UserAccount> users;

    public static void main(String[] args)
    {
        // test
        UserAccount ua = new UserAccount();
        ua.id = 1;
        ua.username = "user";
        ua.bio = "bio";
        users = new ArrayList<>();
        users.add(ua);
        System.out.println(users.get(0).id);
        storeUsers();

        users = null;
        retrieveUsers();
        System.out.println(users.get(0).id);
    }

    /**
     * Validates credentials and returns the user id if they are correct;
     * returns -1 if they are not.
     * @param username
     * @param password
     * @return
     */
    public int getCredentialsID(String username, String password)
    {
        retrieveUsers();

        //credentials validation logic
        for(UserAccount uc : users)
        {
            if(uc.username.equals(username) && uc.password.equals(password))
            {
                return uc.id;
            }
        }
        return -1;
    }

    public boolean usernameExists(String username) {
        //username validation logic
        for(UserAccount ua : users)
        {
            if(ua.username.equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public void saveNewUser(UserAccount user) {
        users.add(user);
        storeUsers();
    }

    public void deleteUser(int id) {
        for(UserAccount ua : users)
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
    public void updateUser(UserAccount user) {
        for(UserAccount ua : users)
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
