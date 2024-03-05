package refactored.controllers;

import javax.swing.JFrame;

import refactored.refactoring.nonui.User;
import refactored.model.UserDBManager;

public class SignInController {
    public static void onSignInClicked(String username, String password, User user, JFrame thisPage) {
        System.out.println(username + " <-> " + password);
        if (UserDBManager.verifyCredentials(username, password))
        {
            System.out.println("It worked");
            UIManager.signInTransition(user, thisPage);
        }
        else
        {
            System.out.println("It Didn't");
        }
    }
}
