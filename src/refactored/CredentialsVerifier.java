package refactored;

import refactored.model.UserDBManager;

public class CredentialsVerifier
{
    public boolean isVerifiedCredentials(String username, String password)
    {
        return UserDBManager.verifyCredentials(username, password);
    }

    public boolean isValidUsername(String username)
    {
        return !UserDBManager.usernameExists(username);
    }
}