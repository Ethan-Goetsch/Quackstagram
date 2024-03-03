package refactored;

import java.time.LocalDateTime;

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

        Content testContent = new ContentBuilder()
        .withPicture(null)
        .withDate(LocalDateTime.now())
        .build();
    }
}