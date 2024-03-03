package refactored.model;

import refactored.stored.UserCredentials;

import java.util.ArrayList;

public abstract class UserCredentialsDBManager extends DBManager<UserCredentials>
{
    private static ArrayList<UserCredentials> credentials;

    public static void main(String[] args)
    {
        // test
        UserCredentials uc = new UserCredentials();
        uc.id = 1;
        uc.username = "user";
        uc.password = "pass";
        credentials = new ArrayList<>();
        credentials.add(uc);
        System.out.println(credentials.get(0).id);
        storeCredentials();

        credentials = null;
        retrieveCredentials();
        System.out.println(credentials.get(0).id);

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
        retrieveCredentials();

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

    /**
     * If credentials are not loaded, load them
     * // Might want to replace with something that rereads the credentials every time. 
     */
    private static void retrieveCredentials()
    {
        if(credentials == null)
            credentials = retrieve(Paths.userCredentialsPath);
    }

    private static void storeCredentials()
    {
        store(credentials, Paths.userCredentialsPath);
    }
}