package refactored.ui.notifications;

import refactored.model.NotificationQuery;
import refactored.ui.IPageController;
import refactored.ui.UIManager;

public class NotificationController implements IPageController
{
    private final UIManager manager;
    private final int userId;

    private final NotificationPage page;
    
    public NotificationController(UIManager manager, int userId)
    {
        this.manager = manager;
        this.userId = userId;
        this.page = new NotificationPage(new NotificationQuery(userId).getNotifications());
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