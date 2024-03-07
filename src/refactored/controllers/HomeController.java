package refactored.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import refactored.entities.Post;
import refactored.entities.interactions.LikeType;
import refactored.model.LikeDBManager;
import refactored.model.UserDBManager;

public class HomeController {
    public static void handleLikeAction(Post post, JLabel likesLabel)
    {
        LikeDBManager.createLike(UserDBManager.currentID, post.getID(), LikeType.LIKE);
        post.like();
        likesLabel.setText("Likes: " + post.getLikeCount());
    }

    private void displayImage(Post post)
    {
        imageViewPanel.removeAll(); // Clear previous content

        JLabel likesLabel = new JLabel("Likes: " + post.getLikeCount()); // Update this line

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

        JButton likeButton = new JButton("❤");
        likeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        likeButton.setBackground(LIKE_BUTTON_COLOR); // Set the background color for the like button
        likeButton.setOpaque(true);
        likeButton.setBorderPainted(false); // Remove border
        likeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               handleLikeAction(imageId, likesLabel); // Update this line
               refreshDisplayImage(postData, imageId); // Refresh the view
            }
        });
       
        // Information panel at the bottom
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(post.getText())); // Description
        infoPanel.add(new JLabel("Likes " + post.getLikeCount())); // Likes
        infoPanel.add(likeButton);

        imageViewPanel.add(fullSizeImageLabel, BorderLayout.CENTER);
        imageViewPanel.add(infoPanel, BorderLayout.SOUTH);
        imageViewPanel.add(userPanel,BorderLayout.NORTH);
            
        imageViewPanel.revalidate();
        imageViewPanel.repaint();

        cardLayout.show(cardPanel, "ImageView"); // Switch to the image view
    }
}
