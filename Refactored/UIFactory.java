package Refactored;

import javax.swing.JFrame;

public class UIFactory
{
    public static JFrame createFrameForPage(PageType pageType)
    {
        return new JFrame();
    }
}