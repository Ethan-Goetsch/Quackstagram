package Refactored;

import javax.swing.JFrame;

public class UIManager
{
    private JFrame currentFrame;
    private PageType currentPageType;

    public void DisplayPage(PageType pageType)
    {
        setFrameAndPage(pageType);
    }

    public void setFrameAndPage(PageType pageType)
    {
        var frame = UIFactory.createFrameForPage(pageType);
        if (currentFrame != null)
            currentFrame.dispose();

        currentFrame = frame;
        currentPageType = pageType;
    }
}