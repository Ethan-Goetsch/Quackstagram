package refactored.ui.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import refactored.entities.Post;
import refactored.factories.UIElementFactory;
import refactored.model.PostDBManager;
import refactored.model.UserDBManager;

public class HomePage extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int IMAGE_WIDTH = WIDTH - 100; // Width for the image posts
    private static final int IMAGE_HEIGHT = 150; // Height for the image posts
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95); // Color for the like button

    // Home panel
    private JPanel homePanel;
    // posts panel
    private CardLayout cardLayout;
    private JPanel cardPanel;
    // fullscreen post panel
    private JPanel fullscreenPostPanel;

    public HomePage()
    {
        setTitle("Quakstagram Home");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        homePanel = new JPanel(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        fullscreenPostPanel = new JPanel(new BorderLayout());

        initializeUI();

        cardPanel.add(homePanel, "Home");
        cardPanel.add(fullscreenPostPanel, "ImageView");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Home"); // Start with the home view
        
        // Header
        JPanel headerPanel = UIElementFactory.createHeaderPanel(WIDTH, "🐥 Quackstagram 🐥");
        add(headerPanel, BorderLayout.NORTH);

        // Navigation Panel
        JPanel navigationPanel = UIElementFactory.createNavigationPanel(this);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    private void initializeUI()
    {
        // Content Scroll Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertical box layout
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Never allow horizontal scrolling

        populateContentPanel(contentPanel);
        add(scrollPane, BorderLayout.CENTER);


        // Set up the home panel
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        homePanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void populateContentPanel(JPanel panel)
    {
        PostDBManager.UserFolloweePosts userFolloweePosts = new PostDBManager.UserFolloweePosts(UserDBManager.currentID);

        if(userFolloweePosts != null)
            for (Post post : userFolloweePosts)
            {
                JPanel itemPanel = createPostPanel(post);
                panel.add(itemPanel);

                // Grey spacing panel
                JPanel spacingPanel = new JPanel();
                spacingPanel.setPreferredSize(new Dimension(WIDTH-10, 5)); // Set the height for spacing
                spacingPanel.setBackground(new Color(230, 230, 230)); // Grey color for spacing
                panel.add(spacingPanel);
            }
    }

    private JPanel createPostPanel(Post post) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.WHITE); // Set the background color for the item panel
        itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        itemPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(UserDBManager.getAuthorUsername(post));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Crop the image to the fixed size
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imageLabel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border to image label
        try
        {
            BufferedImage originalImage = ImageIO.read(new File(post.getFilePath().toString()));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, Math.min(originalImage.getWidth(), IMAGE_WIDTH), Math.min(originalImage.getHeight(), IMAGE_HEIGHT));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            imageLabel.setIcon(imageIcon);
        }
        catch (IOException ex)
        {
            // Handle exception: Image file not found or reading error
            imageLabel.setText("Image not found");
        }

        JLabel descriptionLabel = new JLabel(post.getText());
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel likesLabel = new JLabel("Likes: " + post.getLikeCount());
        likesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
        likeButton.addActionListener(e -> HomeController.handleLikeAction(post, likesLabel));

        itemPanel.add(nameLabel);
        itemPanel.add(imageLabel);
        itemPanel.add(descriptionLabel);
        itemPanel.add(likesLabel);
        itemPanel.add(likeButton);

        // Make the image clickable
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HomeController.onPostClicked(post);
            }
        });

        return itemPanel;
    }

    public void fullscreenPost(Post post)
    {
        fullscreenPostPanel.removeAll(); // Clear previous content

        // Display the image
        JLabel fullSizeImageLabel = new JLabel();
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);

        try
        {
            BufferedImage originalImage = ImageIO.read(new File(post.getFilePath().toString()));
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, Math.min(originalImage.getWidth(), WIDTH-20), Math.min(originalImage.getHeight(), HEIGHT-40));
            ImageIcon imageIcon = new ImageIcon(croppedImage);
            fullSizeImageLabel.setIcon(imageIcon);
        } catch (IOException ex)
        {
            // Handle exception: Image file not found or reading error
            fullSizeImageLabel.setText("Image not found");
        }

        //User Info 
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));
        JLabel userName = new JLabel(UserDBManager.getAuthorUsername(post));
        userName.setFont(new Font("Arial", Font.BOLD, 18));
        userPanel.add(userName);//User Name

        // Like button
        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
       
        // Information panel at the bottom
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(post.getText())); // Description
        JLabel likesLabel = new JLabel("Likes " + post.getLikeCount());
        infoPanel.add(likesLabel); // Likes
        infoPanel.add(likeButton);

        // Like button action listener
        likeButton.addActionListener(e -> HomeController.handleLikeAction(post, likesLabel));

        fullscreenPostPanel.add(fullSizeImageLabel, BorderLayout.CENTER);
        fullscreenPostPanel.add(infoPanel, BorderLayout.SOUTH);
        fullscreenPostPanel.add(userPanel,BorderLayout.NORTH);
            
        fullscreenPostPanel.revalidate();
        fullscreenPostPanel.repaint();

        cardLayout.show(cardPanel, "ImageView"); // Switch to the image view
    }
}
