package refactored.refactoring;

import javax.swing.*;

import refactored.controllers.ProfileController;
import refactored.factories.UIElementFactory;
import refactored.model.PostDBManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.nio.file.Path;

public class ProfileUI extends JFrame {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    
    private JPanel headerPanel;   // Panel for the header
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private JPanel navigationPanel; // Panel for the navigation

    private boolean isCurrentUser;
    private int profileUserID;

    // TODO: Actions, Functions and Observer Pattern in java
    public ProfileUI(int profileUserID) {

        this.profileUserID = profileUserID;
        isCurrentUser = ProfileController.isCurrentUser(profileUserID);

        setTitle("DACS Profile");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI() {
        getContentPane().removeAll(); // Clear existing components
        
        headerPanel = UIElementFactory.createProfileHeader(profileUserID, isCurrentUser); // state-dependent : either follow button or edit profile button
        /// content grid //TODO : Make selected images fullscreen
        JScrollPane contentScrollPane = UIElementFactory.imageGridPanel(GRID_IMAGE_SIZE, new PostDBManager.UserPosts(profileUserID), new ProfileController.ImageClickListener());
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);
        navigationPanel = UIElementFactory.createNavigationPanel(this); // state-independent : always opens the current user profile

        // Re-add the header and navigation panels
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void displayImage(ImageIcon imageIcon) {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        // Add the full-size image to the panel
        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        // Add a back button to return to the grid view
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            getContentPane().removeAll(); // Remove all components from the frame
            initializeUI(); // Re-initialize the UI
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
