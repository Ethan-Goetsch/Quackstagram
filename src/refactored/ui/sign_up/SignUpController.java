package refactored.ui.sign_up;


import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class SignUpController implements IPageController
{
    private final UIManager manager;
    private final SignUpPage page;

    public SignUpController(UIManager manager)
    {
        this.manager = manager;
        this.page = new SignUpPage(() -> handleProfilePictureUpload(), () -> handleRegister(), () -> handleSignIn());
    }

    @Override
    public void open()
    {
        page.setVisible(true);
    }

    @Override
    public void close()
    {
        page.dispose();
    }

    private void handleProfilePictureUpload()
    {
        String username = page.getUsername();

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(page) == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            manager.saveProfilePicture(selectedFile, username);
        }
    }

    private void handleRegister()
    {
        String username = page.getUsername();
        String password = page.getPassword();
        String bio = page.getBio();

        if (!manager.isValidUsername(username))
        {
            JOptionPane.showMessageDialog(page, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            manager.signUp(username, password, bio);
        }
    }

    public void handleSignIn()
    {
        manager.openSignIn();
    }
}