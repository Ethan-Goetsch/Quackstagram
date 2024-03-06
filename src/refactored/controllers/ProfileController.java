package refactored.controllers;

import javax.swing.JButton;
import javax.swing.JFrame;

import refactored.entities.interactions.FollowType;
import refactored.model.FollowDBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.ProfileUI;

public class ProfileController {

    public static void handleEditAction()
    {

    }

    public static void handleFollowAction(int usernameToFollow, JButton followButton)
    {
        if (FollowDBManager.isAFollowingB(UserDBManager.currentID, usernameToFollow))
        {
            FollowDBManager.createFollow(UserDBManager.currentID, usernameToFollow, FollowType.UNFOLLOW);
            followButton.setText(FollowType.FOLLOW.toString());
        }
        else
        {
            FollowDBManager.createFollow(UserDBManager.currentID, usernameToFollow, FollowType.FOLLOW);
            followButton.setText(FollowType.UNFOLLOW.toString());
        }
    }

    public static void openProfileUI(int profileUserID, JFrame thisUI)
    {
        thisUI.dispose();
        JFrame profileUI = new ProfileUI(profileUserID);
        profileUI.setVisible(true);
    }

    public static boolean isCurrentUser(int profileUserID) {
        return UserDBManager.currentID == profileUserID;
    }
}
