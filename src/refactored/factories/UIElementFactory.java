package refactored.factories;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import refactored.controllers.UIManager;

public class UIElementFactory {

    private static final int NAV_ICON_SIZE = 20;

    public static JPanel createHeader(int width, String title) {
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

    private static JButton createNavButton(PageType pageType, Path iconPath, JFrame topFrame) {
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
}
