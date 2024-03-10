package refactored.ui.profile;

import java.util.List;

import refactored.entities.Post;
import refactored.model.FollowDBManager;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class ProfileController implements IPageController
{
    private final UIManager manager;
    private final ProfilePage page;

    private final int userID;
    private final boolean isOwner;

    public ProfileController(UIManager uiManager, int userID, boolean isOwner, boolean isFollowing, List<Post> posts)
    {
        this.manager = uiManager;
        this.userID = userID;
        this.page = new ProfilePage(userID, isOwner, isFollowing, id -> handleFollowOrEditAction(id), pageType -> manager.navigateToPage(pageType), posts);
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
        FollowDBManager.createFollow(userID, userIdToFollow);

        boolean isFollowing = FollowDBManager.isAFollowingB(manager.getCurrentUserId(), userIdToFollow);
        int followees = FollowDBManager.getFolloweeCount(userIdToFollow);

        page.updateEditOrFollowButtonLabel(isFollowing ? "Unfollow" : "Follow");
        page.updateFolloweeCount(followees); // followers actually retrieved in updateFollowerCount
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