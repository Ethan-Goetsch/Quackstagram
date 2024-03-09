package refactored.ui.sign_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import refactored.CredentialsVerifier;
import refactored.model.UserDBManager;
import refactored.ui.UIManager;

public class SignInController
{
    private class SignInListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            handleSignIn(signInPage.getUsername(), signInPage.getPassword());
        }
    }

    private class SignUpListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            handleSignUp();
        }
    }

    private final UIManager uiManager;
    private final CredentialsVerifier credentialsVerifier;
    private final SignInPage signInPage;

    public SignInController(UIManager uiManager, CredentialsVerifier credentialsVerifier)
    {
        this.uiManager = uiManager;
        this.credentialsVerifier = credentialsVerifier;

        signInPage = new SignInPage(new SignInListener(), new SignUpListener());
    }

    public void Open()
    {
        signInPage.setVisible(true);
    }

    public void Close()
    {
        signInPage.setVisible(false);
    }

    private void handleSignIn(String username, String password)
    {
        System.out.println(username + " <-> " + password);
        if (credentialsVerifier.isVerifiedCredentials(username, password))
        {
            System.out.println("It worked; UserID: " + UserDBManager.currentID);
            uiManager.signIn();
        }
        else
        {
            System.out.println("It Didn't");
        }
    }

    private void handleSignUp()
    {
        uiManager.signUp();
    }
}