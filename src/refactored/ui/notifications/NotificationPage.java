package refactored.ui.notifications;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import refactored.entities.interactions.Notification;
import refactored.factories.UIElementFactory;
import refactored.util.TimeFormatter;

public class NotificationPage extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;

    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;

    public NotificationPage(List<Notification> notifications)
    {
        setTitle("Notifications");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI(notifications);
    }

    private void initializeUI(List<Notification> notifications)
    {
        // Reuse the header and navigation panel creation methods from the InstagramProfileUI class
        headerPanel = UIElementFactory.createHeaderPanel(WIDTH, "Notifications 🐥");
        navigationPanel = UIElementFactory.createNavigationPanel(this);

        // Content Panel for notifications
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // populate the notifications panel
        for(Notification notification : notifications)
        {
            // Format the notification message
            String notificationMessage = notification.getNotificationMessage() + " :: " + TimeFormatter.getElapsedTime(notification.getTimestamp()) + " ago";

            // Add the notification to the panel
            JPanel notificationPanel = new JPanel(new BorderLayout());
            notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel notificationLabel = new JLabel(notificationMessage);
            notificationPanel.add(notificationLabel, BorderLayout.CENTER);

            // Add profile icon (if available) and timestamp
            // ... (Additional UI components if needed)

            contentPanel.add(notificationPanel);
        }

        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
    }
}