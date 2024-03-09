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
import refactored.model.FollowDBManager;
import refactored.model.LikeDBManager;
import refactored.model.PostDBManager;
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
    private final CredentialsVerifier credentialsVerifier;

    private IPageController signInPage;
    private IPageController signUpPage;
    private IPageController homePage;
    private IPageController explorePage;
    private IPageController uploadPage;
    private IPageController notificationPage;
    private IPageController profilePage;

    private IPageController activePage;
    private int currentUserId;

    public UIManager(ApplicationManager applicationManager, CredentialsVerifier credentialsVerifier)
    {
        this.applicationManager = applicationManager;
        this.credentialsVerifier = credentialsVerifier;
    }

    public void openSignUp()
    {
        signUpPage = new SignUpController(this);
        setActivePage(signUpPage);
    }

    public void openSignIn()
    {
        signInPage = new SignInController(this);
        setActivePage(signInPage);
    }

    public void openHome(int userId)
    {
        homePage = new HomeController(this);
        setActivePage(homePage);
    }

    public void openExplore(int userId)
    {
        explorePage = new ExploreController(this, PostDBManager.getUserPosts(currentUserId));
        setActivePage(explorePage);
    }

    private void openUpload(int userId)
    {
        uploadPage = new UploadController(this, userId, UserDBManager.getUsername(userId));
        setActivePage(uploadPage);
    }

    private void openNotifications(int userId)
    {
        notificationPage = new NotificationController(this, currentUserId);
        setActivePage(notificationPage);
    }

    public void openProfile(int userId)
    {
        profilePage = new ProfileController(this, userId, currentUserId == userId, FollowDBManager.isAFollowingB(currentUserId, userId), PostDBManager.getUserPosts(userId));
        setActivePage(profilePage);
    }

    public void navigateToPage(PageType page)
    {
        switch (page)
        {
            case EXPLORE:
                openExplore(currentUserId);
                break;
            case HOME:
                openHome(currentUserId);
                break;
            case NOTIFICATION:
                openNotifications(currentUserId);
                break;
            case PROFILE:
                openProfile(currentUserId);
                break;
            case UPLOAD:
                openUpload(currentUserId);
                break;
            default:
                break;
        }
    }

    public void signIn(String username, String password)
    {
        currentUserId = UserDBManager.getUserId(username, password);
        openHome(currentUserId);
    }

    public void signUp(String username, String password, String bio)
    {
        UserDBManager.createUser(username, password, bio);
        openSignIn();
    }

    public void saveProfilePicture(File file, String username)
    {
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
        LikeDBManager.createLike(UserDBManager.currentID, post.getID());
    }

    public void savePost(int userId, String fileName, String caption)
    {
        PostDBManager.createPost(userId, fileName, caption);
    }

    public boolean isVerifiedCredentials(String username, String password)
    {
        return credentialsVerifier.isVerifiedCredentials(username, password);
    }

    public boolean isValidUsername(String username)
    {
        return credentialsVerifier.isValidUsername(username);
    }

    public int createContentId()
    {
        return PostDBManager.generateContentId();
    }

    private void setActivePage(IPageController page)
    {
        if (activePage != null)
            activePage.close();
        activePage = page;
        activePage.open();
    }
}