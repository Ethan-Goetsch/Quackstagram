package refactored.factories;

import javax.swing.JFrame;

public class UIManager {
    //work in progress
    public <T extends JFrame, U extends JFrame> void openUI(T thisUI) {
        // Open InstagramProfileUI frame
        thisUI.dispose();
        U newUI = new U();
        newUI.setVisible(true);
    }   
}
