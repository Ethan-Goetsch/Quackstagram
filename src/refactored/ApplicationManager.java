package refactored;

public class ApplicationManager
{
    private final DatabaseManager DatabaseManager;

    private UIManager uiManager;
    private CredentialsVerifier credentialsVerifier;

    public ApplicationManager(DatabaseManager databaseManager)
    {
        DatabaseManager = databaseManager;

        uiManager = new UIManager();
        uiManager.DisplayPage(PageType.SignIn);
    }

    private void onSignIn()
    {
        if (credentialsVerifier.isVerifiedCredentials(null, null))
            uiManager.setFrameAndPage(PageType.Home);
    }
}