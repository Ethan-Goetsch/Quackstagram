package refactored.model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import refactored.stored.UserAccount;
import refactored.stored.UserCredentials;

public abstract class UserDBManager
{
    private static ArrayList<UserCredentials> credentials;
    private static ArrayList<UserAccount> users;

    /**
     * Validates credentials and returns the user id if they are correct;
     * returns -1 if they are not.
     * @param username
     * @param password
     * @return
     */
    public int getCredentialsID(String username, String password)
    {
        if(areCredentialsLoaded() == false)
        {
            return -1;
        }

        //credentials validation logic
        for(UserCredentials uc : credentials)
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
        for(UserCredentials uc : credentials)
        {
            if(uc.username.equals(username))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the user account with the given id;
     * returns null if it does not exist.
     * @param id
     * @return
     */
    public UserAccount getUser(int id)
    {
        if(areUsersLoaded() == false)
        {
            return null;
        }

        //user retrieval logic
        for(UserAccount ua : users)
        {
            if(ua.id == id)
            {
                return ua;
            }
        }
        return null;
    }

    private boolean areCredentialsLoaded()
    {
        if (credentials == null)
        {
            retrieveCredentials();
        } if (credentials == null)
        {
            return false;
        }
        return true;
    }

    /**
     * users existence logic
     */
    private boolean areUsersLoaded()
    {
        if (users == null)
        {
            retrieveUsers();
        } if (credentials == null)
        {
            return false;
        }
        return true;
    }

    private void retrieveCredentials()
    {
        try{
            File fin = Paths.credentialsPath.toFile();
            FileInputStream fis = new FileInputStream(fin);
            ObjectInputStream ois = new ObjectInputStream(fis);
            credentials = (ArrayList<UserCredentials>) ois.readObject();
        } catch (Exception e)
        {
            //maybe do something here
            e.printStackTrace();
        }
    }

    private void retrieveUsers()
    {
        try{
            File fin = Paths.usersPath.toFile();
            FileInputStream fis = new FileInputStream(fin);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<UserAccount>) ois.readObject();
        } catch (Exception e)
        {
            //maybe do something here
            e.printStackTrace();
        }
    }

    //TODO: implement save new user+credentials
    //TODO: implement delete user+credentials
    //TODO: implement update user
    private void saveCredentials()
    {
        try{
            File fout = Paths.usersPath.toFile();
            FileOutputStream fos = new FileOutputStream(fout);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(credentials);
        } catch (Exception e)
        {
            //maybe do something here
            e.printStackTrace();
        }
    }
}