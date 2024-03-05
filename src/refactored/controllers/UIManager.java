package refactored.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import refactored.factories.PageType;
import refactored.refactoring.ExploreUI;
import refactored.refactoring.HomeUI;
import refactored.refactoring.NotificationUI;
import refactored.refactoring.ProfileUI;
import refactored.refactoring.UploadUI;
import refactored.refactoring.nonui.User;

public abstract class UIManager {
    //work in progress
    public static <T extends JFrame> void transition(PageType pageType, T thisPage) {
        // open the new page
        JFrame newUI = null;
        switch (pageType) {
            case HOME:
                if (thisPage instanceof HomeUI)
                    return;
                thisPage.dispose();
                newUI = new HomeUI();
                break;
            case EXPLORE:
                if (thisPage instanceof ExploreUI)
                    return;
                thisPage.dispose();
                newUI = new ExploreUI();
                break;
            case UPLOAD:
                if (thisPage instanceof UploadUI)
                    return;
                thisPage.dispose();
                newUI = new UploadUI();
                break;
            case NOTIFICATION:
                if (thisPage instanceof NotificationUI)
                    return;
                thisPage.dispose();
                newUI = new NotificationUI();
                break;
            case PROFILE:
                if(thisPage instanceof ProfileUI)
                    return;
                openProfileUI(thisPage);
                break;
            default:
        }
        newUI.setVisible(true);
    }

    //TODO: change the model behind this method
    private static void openProfileUI(JFrame thisPage)
    {
        thisPage.dispose();

        String loggedInUsername = "";

        // Read the logged-in user's username from users.txt
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "users.txt")))
        {
            String line = reader.readLine();
            if (line != null)
            {
                loggedInUsername = line.split(":")[0].trim();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        User user = new User(loggedInUsername);
        ProfileUI profileUI = new ProfileUI(user);
        profileUI.setVisible(true);
    }
}
