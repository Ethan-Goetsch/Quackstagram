package refactored.ui;

import java.awt.Color;
import javax.swing.JFrame;

import refactored.factories.PageType;

public abstract class UIManager
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int NAV_ICON_SIZE = 20; // Corrected static size for bottom icons
    private static final int IMAGE_WIDTH = WIDTH - 100; // Width for the image posts
    private static final int IMAGE_HEIGHT = 150; // Height for the image posts
    private static final Color LIKE_BUTTON_COLOR = new Color(255, 90, 95); // Color for the like button

    private JFrame currentFrame;

    public void displayPage(PageType pageType)
    {
        if (currentFrame != null)
            currentFrame.dispose();

        switch (pageType)
        {
            case Explore:
                currentFrame = new ExploreUI(WIDTH, HEIGHT, NAV_ICON_SIZE, data -> displayPage(data), WIDTH / 3);
                break;
            case Home:
                currentFrame = new HomeUI(WIDTH, HEIGHT, NAV_ICON_SIZE, data -> displayPage(data));
                break;
            case Profile:
                currentFrame = new ProfileUI(WIDTH, HEIGHT, NAV_ICON_SIZE, data -> displayPage(data));
                break;
            case Upload:
                currentFrame = new ImageUploadUI(WIDTH, HEIGHT, NAV_ICON_SIZE, data -> displayPage(data));
                break;
            default:
        }
    }
}