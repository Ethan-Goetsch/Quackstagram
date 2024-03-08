package refactored.controllers;

public abstract class ApplicationManager
{
    // public static currentID; // Should be moved from UserDBManager to here

    public static void main(String[] args)
    {
        SignInController.openSignInUI();
    }
}