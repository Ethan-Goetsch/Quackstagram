package refactored.ui.profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.List;

import refactored.entities.Post;
import refactored.factories.Paths;
import refactored.factories.UIElementFactory;
import refactored.model.FollowDBManager;
import refactored.model.PostDBManager;
import refactored.model.UserDBManager;
import refactored.ui.PageType;
import refactored.util.generic.functions.IAction;


public class ProfilePage extends JFrame
{
    private class PostClickedListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    
    private final boolean isOwner;
    private final int userId;

    private final IAction<Integer> followAction;
    private final IAction<PageType> navigateAction;

    private final List<Post> posts;

    private JPanel headerPanel;   // Panel for the header
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private JPanel navigationPanel; // Panel for the navigation

    private JButton editOrFollowButton;
    private JLabel followersLabel;

    private boolean isFollowing;

    public ProfilePage(int userId, boolean isOwner, boolean isFollowing, IAction<Integer> followAction, IAction<PageType> navigateAction, List<Post> posts)
    {
        this.userId = userId;
        this.isOwner = isOwner;
        this.isFollowing = isFollowing;

        this.followAction = followAction;
        this.navigateAction = navigateAction;

        this.posts = posts;

        setTitle("DACS Profile");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI()
    {
        getContentPane().removeAll(); // Clear existing components
        
        headerPanel = createProfileHeader(); // state-dependent : either follow button or edit profile button

        /// content grid //TODO : Make selected images fullscreen
        JScrollPane contentScrollPane = UIElementFactory.createImageGridPanel(GRID_IMAGE_SIZE, posts, new PostClickedListener());
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);
        navigationPanel = UIElementFactory.createNavigationPanel(this, navigateAction); // state-independent : always opens the current user profile

        // Re-add the header and navigation panels
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void displayImage(ImageIcon imageIcon)
    {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        // Add the full-size image to the panel
        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        // Add a back button to return to the grid view
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->
        {
            getContentPane().removeAll(); // Remove all components from the frame
            initializeUI(); // Re-initialize the UI
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JPanel createProfileHeader()
    {
        // profile's user account
        String username = UserDBManager.getUsername(userId);
        String bio = UserDBManager.getBio(userId);
        int postsCount = PostDBManager.getPostCount(userId);
        int followingCount = FollowDBManager.getFollowerCount(userId);
        int followeeCount = FollowDBManager.getFolloweeCount(userId);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);
        
        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile image
        ImageIcon profileIcon = new ImageIcon(new ImageIcon(Path.of(Paths.profilePicturesPath.toString(), username + ".png").toString()).getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user " + postsCount);
        statsPanel.add(createStatLabel(Integer.toString(postsCount) , "Posts"));
        followersLabel = createStatLabel(Integer.toString(followeeCount), "Followers");
        statsPanel.add(followersLabel);
        statsPanel.add(createStatLabel(Integer.toString(followingCount), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding
        
        // Follow or Edit Profile Button (depending on the user)
        editOrFollowButton = new JButton(isOwner ? "Edit Profile" : isFollowing ? "Unfollow" : "Follow");
        editOrFollowButton.addActionListener(e -> followAction.execute(userId));
        
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

    private JLabel createStatLabel(String number, String text)
    {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }


    public void updateEditOrFollowButtonLabel(String string)
    {
        editOrFollowButton.setText(string);
    }

    public void updateFollowerCount(int followerCount)
    {
        followersLabel.setText("<html><div style='text-align: center;'>" + followerCount + "<br/>" + "Followers" + "</div></html>");
    }
}