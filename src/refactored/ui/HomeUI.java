package refactored.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HomeUI extends JFrame
{
    private final UIManager manager;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel homePanel;
    private JPanel imageViewPanel;

    public HomeUI(UIManager manager)
    {
        this.manager = manager;

        setTitle("Quakstagram Home");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        homePanel = new JPanel(new BorderLayout());
        imageViewPanel = new JPanel(new BorderLayout());

        // Content Panel
        JPanel contentPanel = manager.createContentPanel();

        // Scroll Pane
        JScrollPane scrollPane = manager.createScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        homePanel.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(homePanel, "Home");
        cardPanel.add(imageViewPanel, "ImageView");

        add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, "Home"); // Start with the home view
        
        // Header Panel (reuse from InstagramProfileUI or customize for home page)
        // Header with the Register label
        JPanel headerPanel = manager.createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Navigation Bar
        JPanel navigationPanel = manager.createNavigationPanel();
        add(navigationPanel, BorderLayout.SOUTH);

        initializeUI();
    }

    private void initializeUI()
    {

    }
}
