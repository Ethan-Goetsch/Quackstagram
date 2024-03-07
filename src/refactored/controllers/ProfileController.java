package refactored.controllers;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import refactored.entities.Post;
import refactored.entities.interactions.FollowType;
import refactored.model.FollowDBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.ProfileUI;

public class ProfileController {

    public static ProfileUI profileUI;

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
        profileUI = new ProfileUI(profileUserID);
        profileUI.setVisible(true);
    }

    public static boolean isCurrentUser(int profileUserID) {
        return UserDBManager.currentID == profileUserID;
    }
}
