package refactored;

import refactored.ui.UIManager;

public class ApplicationManager
{
    public static void main(String[] args)
    {
        ApplicationManager applicationManager = new ApplicationManager();
        CredentialsVerifier credentialsVerifier = new CredentialsVerifier();

        UIManager uiManager = new UIManager(applicationManager, credentialsVerifier);
        uiManager.openSignIn();
    }
}