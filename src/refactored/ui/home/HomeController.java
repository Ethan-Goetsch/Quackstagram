package refactored.ui.home;

import refactored.entities.Post;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class HomeController implements IPageController
{
    private final UIManager manager;
    private final HomePage page;

    public HomeController(UIManager manager)
    {
        this.manager = manager;
        this.page = new HomePage(post -> handleLikeAction(post), post -> onPostClicked(post), pageType -> manager.navigateToPage(pageType));
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

    private void handleLikeAction(Post post)
    {
        manager.likePost(post);
    }

    private void onPostClicked(Post post)
    {
        page.fullscreenPost(post);
    }
}