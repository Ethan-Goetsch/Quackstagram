package refactored.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import refactored.controllers.ExploreController;
import refactored.controllers.ProfileController;
import refactored.entities.Post;
import refactored.factories.UIElementFactory;
import refactored.model.PostDBManager;
import refactored.model.UserDBManager;
import refactored.util.TimeFormatter;

public class ExploreUI extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int IMAGE_SIZE = WIDTH / 3 - 10; // Size for each image in the grid

    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;

    public ExploreUI()
    {
        setTitle("Explore");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI()
    {
        getContentPane().removeAll(); // Clear existing components
        setLayout(new BorderLayout()); // Reset the layout manager

        headerPanel = UIElementFactory.createHeaderPanel(WIDTH, "Explore");
        navigationPanel = UIElementFactory.createNavigationPanel(this);
        contentPanel = createContentPanel();

        // Add panels to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JPanel createContentPanel()
    {
        // Search bar at the top
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(" Search Users");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchField.getPreferredSize().height)); // Limit the height

        // Image grid below the search bar
        JScrollPane imageGridPanel = UIElementFactory.imageGridPanel(IMAGE_SIZE, new PostDBManager.OtherUsersPosts(UserDBManager.currentID), new ExploreController.ImageClickListener());

        // Main content panel that holds both the search bar and the image grid
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.add(searchPanel);
        mainContentPanel.add(imageGridPanel); // This will stretch to take up remaining space
        return mainContentPanel;
    }

    public void displayImage(ImageIcon imageIcon, Post post)
    {
    getContentPane().removeAll();
    setLayout(new BorderLayout());

    String elapsedTime = TimeFormatter.getElapsedTime(post.getTimestamp());

    // Top panel for username and time since posting
    JPanel topPanel = new JPanel(new BorderLayout());
    JButton usernameLabel = new JButton(UserDBManager.getAuthorUsername(post));
    JLabel timeLabel = new JLabel(elapsedTime);
    timeLabel.setHorizontalAlignment(JLabel.RIGHT);

    topPanel.add(usernameLabel, BorderLayout.WEST);
    topPanel.add(timeLabel, BorderLayout.EAST);

    // Prepare the image for display
    JLabel imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setIcon(imageIcon);
    // try
    // {
    //     BufferedImage originalImage = ImageIO.read(new File(imagePath));
    //     ImageIcon imageIcon = new ImageIcon(originalImage);
    //     imageLabel.setIcon(imageIcon);
    // }
    // catch (IOException ex)
    // {
    //     imageLabel.setText("Image not found");
    // }

    // Bottom panel for bio and likes, and back button
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JTextArea bioTextArea = new JTextArea(UserDBManager.getAuthorBio(post));
    bioTextArea.setEditable(false);
    JLabel likesLabel = new JLabel("Likes: " + post.getLikeCount());
    JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton backButton = new JButton("Back");
    
    bottomPanel.add(bioTextArea, BorderLayout.CENTER);
    bottomPanel.add(likesLabel, BorderLayout.CENTER);
    bottomPanel.add(backButtonPanel, BorderLayout.SOUTH);

    // Make the button take up the full width
    backButton.setPreferredSize(new Dimension(WIDTH-20, backButton.getPreferredSize().height));
    backButtonPanel.add(backButton);

    backButton.addActionListener(e ->
    {
        getContentPane().removeAll();
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    });

    usernameLabel.addActionListener(e -> {
        ProfileController.openProfileUI(post.getAuthorID(), this);
    });

    // Container panel for image and details
    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.add(topPanel, BorderLayout.NORTH);
    containerPanel.add(imageLabel, BorderLayout.CENTER);
    containerPanel.add(bottomPanel, BorderLayout.SOUTH);

    // Re-add the header and navigation panels
    add(headerPanel, BorderLayout.NORTH);
    add(navigationPanel, BorderLayout.SOUTH);
    add(containerPanel, BorderLayout.CENTER);

    revalidate();
    repaint();
    }
}   