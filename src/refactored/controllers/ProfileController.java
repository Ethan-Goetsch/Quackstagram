package refactored.controllers;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import refactored.entities.Post;
import refactored.entities.interactions.FollowType;
import refactored.model.FollowDBManager;
import refactored.model.UserDBManager;
import refactored.ui.ProfileUI;

public class ProfileController {

    public static ProfileUI profileUI;

    public static void handleEditAction()
    {

    }

    public static void handleFollowAction(int userIDToFollow)
    {
        FollowDBManager.createFollow(UserDBManager.currentID, userIDToFollow);
        if (FollowDBManager.isAFollowingB(UserDBManager.currentID, userIDToFollow))
        {
            profileUI.updateEditOrFollowButtonLabel("Unfollow");
        }
        else
        {
            profileUI.updateEditOrFollowButtonLabel("Follow");
        }
        profileUI.updateFollowerCount();
    }

    public static class ImageClickListener implements MouseListenerFactory
    {
        public MouseListener createMouseClickListener(ImageIcon imageIcon, Post post)
        {
            return new java.awt.event.MouseAdapter() {
                @Override 
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    profileUI.displayImage(imageIcon);
                }
            };
        }
    }

    public static void openProfileUI(int profileUserID, JFrame thisUI)
    {
        thisUI.dispose();
        boolean isCurrentUser = ProfileController.isCurrentUser(profileUserID);
        profileUI = new ProfileUI(profileUserID, isCurrentUser);
        profileUI.setVisible(true);
    }

    public static boolean isCurrentUser(int profileUserID) {
        return UserDBManager.currentID == profileUserID;
    }
}
