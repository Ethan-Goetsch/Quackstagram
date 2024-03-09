package refactored.ui.sign_in;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import refactored.model.UserDBManager;
import refactored.ui.UIManager;
import refactored.ui.sign_up.SignUpController;

public class SignInController
{
    private SignInPage signInPage;

    public SignInController()
    {
        signInPage = new SignInPage();
    }

    public void Open()
    {

    }

    public void Close()
    {

    }

    public void onSignInClicked(String username, String password, JFrame thisPage)
    {
        System.out.println(username + " <-> " + password);
        if (UserDBManager.verifyCredentials(username, password))
        {
            System.out.println("It worked; UserID: " + UserDBManager.currentID);
            UIManager.signInTransition(thisPage);
        }
        else
        {
            System.out.println("It Didn't");
        }
    }

    public void openSignInUI()
    {
        SwingUtilities.invokeLater(() ->
        {
            signInUI = new SignInPage();
            signInUI.setVisible(true);
        });
    }

    public void onRegisterNowClicked(ActionEvent event)
    {
        signInUI.dispose();
        SwingUtilities.invokeLater(() ->
        {
            SignUpController.openSignUpUI();
        });
    }
}
