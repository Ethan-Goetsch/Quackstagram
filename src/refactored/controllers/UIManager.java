package refactored.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import refactored.factories.PageType;
import refactored.model.DBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.ExploreUI;
import refactored.refactoring.HomeUI;
import refactored.refactoring.NotificationUI;
import refactored.refactoring.ProfileUI;
import refactored.refactoring.SignUpUI;
import refactored.refactoring.UploadUI;

import refactored.refactoring.nonui.User;

public abstract class UIManager {
    //work in progress
    public static <T extends JFrame> void transition(PageType pageType, T thisPage) {
        // open the new page
        JFrame newUI = null;
        Controller controller = null;
        switch (pageType) {
            case SIGNUP:
                if (thisPage instanceof SignUpUI)
                    return;
                thisPage.dispose();
                newUI = new SignUpUI();
                break;
    
            case HOME:
                
                thisPage.dispose();
                HomeController.openHomeUI();
                break;
            case EXPLORE:
                if (thisPage instanceof ExploreUI)
                    return;
                thisPage.dispose();
                ExploreController.openExploreUI();
                break;
            case UPLOAD:
                if (thisPage instanceof UploadUI)
                    return;
                thisPage.dispose();
                UploadController.openUploadUI();
                break;
            case NOTIFICATION:
                if (thisPage instanceof NotificationUI)
                    return;
                thisPage.dispose();
                newUI = new NotificationUI();
                break;
            case PROFILE:
                ProfileController.openProfileUI(UserDBManager.currentID, thisPage);
                return;
        }
        if(newUI != null)
            newUI.setVisible(true);
    }

    public static void signInTransition(JFrame thisPage) {
        thisPage.dispose();
        ProfileController.openProfileUI(UserDBManager.currentID, thisPage);
    }
}
