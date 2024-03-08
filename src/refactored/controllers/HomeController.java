package refactored.controllers;

import javax.swing.JLabel;

import refactored.entities.Post;
import refactored.entities.interactions.LikeType;
import refactored.model.LikeDBManager;
import refactored.model.UserDBManager;
import refactored.refactoring.HomeUI;

public class HomeController {

    private static HomeUI homeUI;
    public static void openHomeUI()
    {
        homeUI = new HomeUI();
        homeUI.setVisible(true);
    } 

    public static void handleLikeAction(Post post, JLabel likesLabel)
    {
        LikeDBManager.createLike(UserDBManager.currentID, post.getID());
        likesLabel.setText("Likes: " + post.getLikeCount());
    }

    public static void onPostClicked(Post post)
    {
        homeUI.fullscreenPost(post);
    }
}
