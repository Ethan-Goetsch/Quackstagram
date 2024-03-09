package refactored.ui.sign_up;


import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import refactored.model.UserDBManager;
import refactored.ui.UIManager;

public class SignUpController
{
    private final UIManager manager;
    private final SignUpPage page;

    public SignUpController(UIManager manager)
    {
        this.manager = manager;
        this.page = new SignUpPage(() -> handleProfilePictureUpload(), () -> handleRegister(), () -> handleSignIn());
    }

    public void open()
    {
        page.setVisible(true);
    }

    public void close()
    {
        page.setVisible(false);
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

        if (manager.isValidUsername(username))
        {
            JOptionPane.showMessageDialog(page, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            UserDBManager.createUser(username, password, bio); // TODO
            manager.openSignIn();
        }
    }

    public void handleSignIn()
    {
        manager.openSignIn();
    }
}