package refactored.ui.sign_up;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import refactored.factories.Paths;
import refactored.model.UserDBManager;
import refactored.ui.sign_in.SignInController;

public class SignUpController {

    private static SignUpPage signUpUI;

    public static void openSignUpUI()
    {
        SwingUtilities.invokeLater(() -> {
            signUpUI = new SignUpPage();
            signUpUI.setVisible(true);
        });
    }

    public static void onRegisterClicked(String username, String password, String bio)
    {
        if (UserDBManager.usernameExists(username))
        {
            JOptionPane.showMessageDialog(signUpUI, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            UserDBManager.createUser(username, password, bio);
            signUpUI.dispose();

            // Open the SignInUI frame
            SwingUtilities.invokeLater(() ->
            {
                SignInController.openSignInUI();
            });
        }
    }


    // Method to handle profile picture upload
    public static void handleProfilePictureUpload(String username)
    {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(signUpUI) == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            saveProfilePicture(selectedFile, username);
        }
    }

    private static void saveProfilePicture(File file, String username)
    {
        try
        {
            BufferedImage image = ImageIO.read(file);
            File outputFile = new File(Path.of(Paths.profilePicturesPath.toString(), username + ".png").toString());
            ImageIO.write(image, "png", outputFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void openSignInUI() {
        // Close the SignInUI frame
        signUpUI.dispose();

        // Open the SignUpUI frame
        SwingUtilities.invokeLater(() ->
        {
            SignInController.openSignInUI();
        });
    }
}
