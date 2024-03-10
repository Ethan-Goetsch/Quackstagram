package refactored.ui.explore;

import refactored.entities.Post;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class ExploreController implements IPageController
{
    private final UIManager manager;
    private final ExplorePage page;

    public ExploreController(UIManager manager, Iterable<Post> posts)
    {
        this.manager = manager;
        this.page = new ExplorePage(id -> openProfile(id), pageType -> manager.navigateToPage(pageType), posts);
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

    private void openProfile(int userId)
    {
        manager.openProfile(userId);
    }
}