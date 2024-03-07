package refactored.controllers;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import refactored.entities.Post;
import refactored.refactoring.ExploreUI;

public class ExploreController {
    public static ExploreUI exploreUI;

    public static void openExploreUI() {
        exploreUI = new ExploreUI();
        exploreUI.setVisible(true);
    }

    public static class ImageClickListener implements MouseListenerFactory
    {
        public MouseListener createMouseClickListener(ImageIcon imageIcon, Post post)
        {
            return new java.awt.event.MouseAdapter() {
                @Override 
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    exploreUI.displayImage(imageIcon, post);
                }
            };
        }
    }
}
