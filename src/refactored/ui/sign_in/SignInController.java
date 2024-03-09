package refactored.ui.sign_in;

import refactored.model.UserDBManager;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class SignInController implements IPageController
{
    private final UIManager manager;
    private final SignInPage page;

    public SignInController(UIManager uiManager)
    {
        this.manager = uiManager;
        this.page = new SignInPage(() -> handleSignIn(), () -> handleSignUp());
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

    private void handleSignIn()
    {
        String username = page.getUsername();
        String password = page.getPassword();

        System.out.println(username + " <-> " + password);
        if (manager.isVerifiedCredentials(username, password))
        {
            System.out.println("It worked; UserID: " + UserDBManager.currentID);
            manager.signIn(username, password);
        }
        else
        {
            System.out.println("It Didn't");
        }
    }

    private void handleSignUp()
    {
        manager.openSignUp();
    }
}