package refactored.refactoring;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import refactored.controllers.UploadController;
import refactored.factories.UIElementFactory;

public class UploadUI extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private JLabel imagePreviewLabel;
    private JTextArea captionTextArea;
    private JButton uploadButton;

    public UploadUI()
    {
        setTitle("Upload Image");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI()
    {
        JPanel headerPanel = UIElementFactory.createHeader(WIDTH, "🐥 Quackstagram 🐥");
        JPanel navigationPanel = UIElementFactory.createNavigationPanel(this); // Reuse the createNavigationPanel method

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Image preview
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));

        // Set an initial empty icon to the imagePreviewLabel
        ImageIcon emptyImageIcon = new ImageIcon();
        imagePreviewLabel.setIcon(emptyImageIcon);

        contentPanel.add(imagePreviewLabel);

        // Caption text area
        captionTextArea = new JTextArea("Enter a caption");
        captionTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        captionTextArea.setLineWrap(true);
        captionTextArea.setWrapStyleWord(true);
        JScrollPane captionScrollPane = new JScrollPane(captionTextArea);
        captionScrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT / 6));
        contentPanel.add(captionScrollPane);

        // Upload button
        uploadButton = new JButton("Upload Image");
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // ask UploadController for proper handler
        uploadButton.addActionListener(e -> UploadController.uploadAction());
        contentPanel.add(uploadButton);

        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }

    public void updateImagePreview(ImageIcon imageIcon)
    {
        // Check if imagePreviewLabel has a valid size
        if (imagePreviewLabel.getWidth() > 0 && imagePreviewLabel.getHeight() > 0) {
            Image image = imageIcon.getImage();

            // Calculate the dimensions for the image preview
            int previewWidth = imagePreviewLabel.getWidth();
            int previewHeight = imagePreviewLabel.getHeight();
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double widthRatio = (double) previewWidth / imageWidth;
            double heightRatio = (double) previewHeight / imageHeight;
            double scale = Math.min(widthRatio, heightRatio);
            int scaledWidth = (int) (scale * imageWidth);
            int scaledHeight = (int) (scale * imageHeight);

            // Set the image icon with the scaled image
            imageIcon.setImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
        }

        imagePreviewLabel.setIcon(imageIcon);
    }

    public String getCaption()
    {
        return captionTextArea.getText();
    }

    public void updateUploadButtonText(boolean imageUploaded)
    {
        if (imageUploaded) {
            uploadButton.setText("Upload Another Image");
        } else {
            uploadButton.setText("Upload Image");
        }
    }
}