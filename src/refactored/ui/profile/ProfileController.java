package refactored.ui.profile;

import java.util.List;

import refactored.entities.Post;
import refactored.model.FollowDBManager;
import refactored.model.UserDBManager;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class ProfileController implements IPageController
{
    private final UIManager manager;
    private final ProfilePage page;

    private final int userId;
    private final boolean isOwner;

    public ProfileController(UIManager uiManager, int userId, boolean isOwner, boolean isFollowing, List<Post> posts)
    {
        this.manager = uiManager;
        this.page = new ProfilePage(userId, isOwner, isFollowing, id -> handleFollowOrEditAction(id), pageType -> manager.navigateToPage(pageType), posts);
        this.userId = userId;
        this.isOwner = isOwner;
    }

    private void handleFollowOrEditAction(int id)
    {
        if (isOwner)
            handleEdit(id);
        else
            handleFollow(id);
    }

    private void handleFollow(int userIdToFollow)
    {
        FollowDBManager.createFollow(UserDBManager.currentID, userIdToFollow);
        boolean isFollowing = FollowDBManager.isAFollowingB(UserDBManager.currentID, userIdToFollow);
        int followers = FollowDBManager.getFollowerCount(userIdToFollow);

        page.updateEditOrFollowButtonLabel(isFollowing ? "Unfollow" : "Follow");
        page.updateFollowerCount(followers);
    }

    private void handleEdit(int userId)
    {
        
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
}