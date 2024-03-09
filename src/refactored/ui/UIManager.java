package refactored.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import refactored.factories.PageType;
import refactored.model.DBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.nonui.User;
import refactored.ui.explore.ExploreController;
import refactored.ui.explore.ExplorePage;
import refactored.ui.home.HomeController;
import refactored.ui.home.HomePage;
import refactored.ui.notifications.NotificationPage;
import refactored.ui.profile.ProfileController;
import refactored.ui.profile.ProfilePage;
import refactored.ui.sign_up.SignUpPage;
import refactored.ui.upload.UploadController;
import refactored.ui.upload.UploadPage;

public abstract class UIManager {
    //work in progress
    public static <T extends JFrame> void transition(PageType pageType, T thisPage) {
        // open the new page
        JFrame newUI = null;
        Controller controller = null;
        switch (pageType) {
            case SIGNUP:
                if (thisPage instanceof SignUpPage)
                    return;
                thisPage.dispose();
                newUI = new SignUpPage();
                break;
    
            case HOME:
                
                thisPage.dispose();
                HomeController.openHomeUI();
                break;
            case EXPLORE:
                if (thisPage instanceof ExplorePage)
                    return;
                thisPage.dispose();
                ExploreController.openExploreUI();
                break;
            case UPLOAD:
                if (thisPage instanceof UploadPage)
                    return;
                thisPage.dispose();
                UploadController.openUploadUI();
                break;
            case NOTIFICATION:
                if (thisPage instanceof NotificationPage)
                    return;
                thisPage.dispose();
                newUI = new NotificationPage();
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
