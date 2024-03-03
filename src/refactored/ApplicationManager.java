package refactored;

import refactored.ui.UIManager;

public class ApplicationManager
{
    private UIManager uiManager;
    private CredentialsVerifier credentialsVerifier;

    public ApplicationManager()
    {
        uiManager = new UIManager();
    }

    public static void main(String[] args)
    {
        ApplicationManager applicationManager = new ApplicationManager();
    }
}