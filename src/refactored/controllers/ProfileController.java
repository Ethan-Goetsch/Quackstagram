package refactored.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;

import refactored.entities.interactions.FollowType;
import refactored.factories.Paths;
import refactored.model.FollowDBManager;
import refactored.model.UserDBManager;

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

    public static boolean isCurrentUser(int profileUserID) {
        return UserDBManager.currentID == profileUserID;
    }
}
