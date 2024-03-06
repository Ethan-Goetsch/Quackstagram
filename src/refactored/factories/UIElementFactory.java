package refactored.factories;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import refactored.controllers.ProfileController;
import refactored.controllers.UIManager;
import refactored.model.FollowDBManager;
import refactored.model.PostDBManager;
import refactored.model.UserDBManager;

public class UIElementFactory {

    private static final int NAV_ICON_SIZE = 20;

    public static JPanel createHeader(int width, String title)
    {
        // Header with the Register label
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel(title);
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white
        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(width, 40)); // Give the header a fixed height
        return headerPanel;
    }

    /**
     * the nav bar needs to know which frame it belongs to in order to close that frame on button click.
     * there are ways of getting the reference of the frame from the JPannel, but it has to attached first. 
     * @param this
     * @return
     */
    public static JPanel createNavigationPanel(JFrame topFrame)
    {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Buttons
        navigationPanel.add(createNavButton(PageType.HOME, Paths.homeIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(PageType.EXPLORE, Paths.searchIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(PageType.UPLOAD, Paths.addIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(PageType.NOTIFICATION, Paths.heartIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(PageType.PROFILE, Paths.profileIconPath, topFrame));

        return navigationPanel;
    }

    private static JButton createNavButton(PageType pageType, Path iconPath, JFrame topFrame)
    {
        ImageIcon iconOriginal = new ImageIcon(iconPath.toString());
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            UIManager.transition(pageType, topFrame);
        });
        return button;
    }

    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    
    public static JPanel createProfileHeader(int profileOwnerID, boolean isCurrentUser)
    {
        // TODO: Give this job to a controller
        // TODO: BAD DESIGN: This is a bad design, we should not be calling the database from the UI. Furthermore, we should addrest the UserDB, and eventually compute the counts in the init part of the UserDBManager main method.
        // TODO: Replace this with a User object
        // profile's user account
        String username = UserDBManager.getUsername(profileOwnerID);
        String bio = UserDBManager.getBio(profileOwnerID);
        int postsCount = PostDBManager.getPostCount(profileOwnerID);
        int followingCount = FollowDBManager.getFollowerCount(profileOwnerID);
        int followeeCount = FollowDBManager.getFolloweeCount(profileOwnerID);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);
        
        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile image
        ImageIcon profileIcon = new ImageIcon(new ImageIcon(Paths.profilePicturesPath + username + ".png").getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user " + postsCount);
        statsPanel.add(createStatLabel(Integer.toString(postsCount) , "Posts"));
        statsPanel.add(createStatLabel(Integer.toString(followeeCount), "Followers"));
        statsPanel.add(createStatLabel(Integer.toString(followingCount), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding
        
        // Follow or Edit Profile Button (depending on the user)
        JButton editOrFollowButton = new JButton();
        if (isCurrentUser) {
            editOrFollowButton.setText("Edit Profile");
            editOrFollowButton.addActionListener(e -> ProfileController.handleEditAction());
        } else {
            // set initial text
            // TODO: BAD DESIGN: This is a bad design, we should not be calling the database from the UI
            if(FollowDBManager.isAFollowingB(UserDBManager.currentID, profileOwnerID))
                editOrFollowButton.setText("Unfollow");
            else
                editOrFollowButton.setText("Follow");

            editOrFollowButton.addActionListener(e -> {ProfileController.handleFollowAction(profileOwnerID, editOrFollowButton);} );
        }
        
        editOrFollowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editOrFollowButton.setFont(new Font("Arial", Font.BOLD, 12));
        editOrFollowButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, editOrFollowButton.getMinimumSize().height)); // Make the button fill the horizontal space
        editOrFollowButton.setBackground(new Color(225, 228, 232)); // A soft, appealing color that complements the UI
        editOrFollowButton.setForeground(Color.BLACK);
        editOrFollowButton.setOpaque(true);
        editOrFollowButton.setBorderPainted(false);
        editOrFollowButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some vertical padding

        // Add Stats and Follow Button to a combined Panel
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));
        statsFollowPanel.add(statsPanel);
        statsFollowPanel.add(editOrFollowButton);
        topHeaderPanel.add(statsFollowPanel, BorderLayout.CENTER);

        headerPanel.add(topHeaderPanel);

        // Profile Name and Bio Panel
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

        JLabel profileNameLabel = new JLabel(username);
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides

        JTextArea profileBio = new JTextArea(bio);
        System.out.println("This is the bio "+username);
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides

        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        headerPanel.add(profileNameAndBioPanel);

        return headerPanel;
    }

    private static JLabel createStatLabel(String number, String text)
    {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }
}
