package refactored;

import refactored.ui.sign_in.SignInController;

public abstract class ApplicationManager
{
    public static void main(String[] args)
    {
        SignInController.openSignInUI();
    }
}