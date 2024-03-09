package refactored.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import refactored.model.UserDBManager;
import refactored.ui.SignInUI;

public class SignInController {
    public static void onSignInClicked(String username, String password, JFrame thisPage) {
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

    private static SignInUI signInUI;

    public static void openSignInUI()
    {
        SwingUtilities.invokeLater(() -> {
            signInUI = new SignInUI();
            signInUI.setVisible(true);
        });
    }

    public static void onRegisterNowClicked(ActionEvent event)
    {
        signInUI.dispose();
        SwingUtilities.invokeLater(() -> {
            SignUpController.openSignUpUI();
        });
    }
}
