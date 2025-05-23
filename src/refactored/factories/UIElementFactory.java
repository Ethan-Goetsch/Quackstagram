package refactored.factories;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import refactored.Paths;
import refactored.entities.Post;
import refactored.ui.PageType;
import refactored.util.generic.functions.IAction;
import refactored.util.generic.functions.IAction2;

public class UIElementFactory
{
    private static final int NAV_ICON_SIZE = 20;

    public static JPanel createHeaderPanel(int width, String title)
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
    public static JPanel createNavigationPanel(JFrame topFrame, IAction<PageType> navigateAction)
    {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Buttons
        navigationPanel.add(createNavButton(navigateAction, PageType.HOME, Paths.homeIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.EXPLORE, Paths.searchIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.UPLOAD, Paths.addIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.NOTIFICATION, Paths.heartIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.PROFILE, Paths.profileIconPath, topFrame));

        return navigationPanel;
    }

    private static JButton createNavButton(IAction<PageType> navigateAction, PageType pageType, Path iconPath, JFrame topFrame)
    {
        ImageIcon iconOriginal = new ImageIcon(iconPath.toString());
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.addActionListener(e -> navigateAction.execute(pageType));
        return button;
    }

    public static JScrollPane createImageGridPanel(int GRID_IMAGE_SIZE, Iterable<Post> posts, IAction2<Post, ImageIcon> imageClickedListener)
    {
        // panel to be decorated with scroll bar
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 3,2, 2)); // Grid layout for image grid

        // get user posts
        for (Post post : posts)
        {
            Path path = post.getFilePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            
            // Add a mouse listener to the image label
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    imageClickedListener.execute(post, imageIcon);
                }
            });
            contentPanel.add(imageLabel);
        }

        // scroll pane decorator
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        return scrollPane;
    }
}