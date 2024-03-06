package refactored.controllers;

import javax.swing.JFrame;

import refactored.model.UserDBManager;

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
}
