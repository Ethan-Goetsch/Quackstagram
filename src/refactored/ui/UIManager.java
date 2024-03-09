package refactored.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import refactored.CredentialsVerifier;
import refactored.factories.Paths;

public class UIManager
{
    private final CredentialsVerifier credentialsVerifier;

    public void signIn()
    {

    }

    public void openSignIn()
    {

    }

    public void saveProfilePicture(File file, String username)
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

    public boolean isVerifiedCredentials(String username, String password)
    {
        return credentialsVerifier.isVerifiedCredentials(username, password);
    }

    public boolean isValidUsername(String username)
    {
        return credentialsVerifier.isValidUsername(username);
    }
}