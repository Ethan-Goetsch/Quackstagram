package refactored.ui.explore;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import refactored.controllers.MouseListenerFactory;
import refactored.entities.Post;

public class ExploreController {
    public static ExplorePage exploreUI;

    public static void openExploreUI() {
        exploreUI = new ExplorePage();
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
