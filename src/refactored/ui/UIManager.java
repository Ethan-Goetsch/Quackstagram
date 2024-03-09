package refactored.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import refactored.ApplicationManager;
import refactored.CredentialsVerifier;
import refactored.entities.Post;
import refactored.factories.Paths;
import refactored.model.LikeDBManager;
import refactored.model.UserDBManager;
import refactored.ui.explore.ExploreController;
import refactored.ui.home.HomeController;
import refactored.ui.notifications.NotificationController;
import refactored.ui.profile.ProfileController;
import refactored.ui.sign_in.SignInController;
import refactored.ui.sign_up.SignUpController;
import refactored.ui.upload.UploadController;

public class UIManager
{
    private final ApplicationManager applicationManager;
    private final UserDBManager userDatabaseManager;
    private final CredentialsVerifier credentialsVerifier;

    private final IPageController signInPage;
    private final IPageController signUpPage;
    private final IPageController homePage;
    private final IPageController explorePage;
    private final IPageController uploadPage;
    private final IPageController notificationPage;
    private final IPageController profilePage;

    private IPageController activePage;

    public void signIn()
    {
        setActivePage(homePage);
    }

    public void signUp(String username, String password, String bio)
    {
        | userDatabaseManager.createUser(username, password, bio);
        setActivePage(signInPage);
    }

    public void saveProfilePicture(File file, String username)
    { | 
        try
        {
            BufferedImage image = ImageIO.read(file);
            File outputFile = new File(Path.of(Paths.profilePicturesPath.toString(), username + ".png").toString()); // TODO
            ImageIO.write(image, "png", outputFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void likePost(Post post)
    {
        | LikeDBManager.createLike(UserDBManager.currentID, post.getID()); // TODO
    }

    public boolean isVerifiedCredentials(String username, String password)
    {
        return credentialsVerifier.isVerifiedCredentials(username, password);
    }

    public boolean isValidUsername(String username)
    {
        return credentialsVerifier.isValidUsername(username);
    }

    private void setActivePage(IPageController page)
    {
        if (activePage != null)
            activePage.close();
        activePage = page;
        activePage.open();
    }
}