package refactored.ui.notifications;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import refactored.entities.interactions.Notification;
import refactored.factories.UIElementFactory;
import refactored.model.NotificationQuery;
import refactored.model.UserDBManager;
import refactored.util.TimeFormatter;

public class NotificationPage extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;

    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel navigationPanel;


    public NotificationPage()
    {
        setTitle("Notifications");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI()
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
        NotificationQuery.NotificationQueryResult notifications = new NotificationQuery.NotificationQueryResult(UserDBManager.currentID);
        for(Notification n : notifications)
        {
            // Format the notification message
            String notificationMessage = n.getNotificationMessage() + " :: " + TimeFormatter.getElapsedTime(n.getTimestamp()) + " ago";

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