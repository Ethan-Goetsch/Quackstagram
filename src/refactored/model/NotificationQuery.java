package refactored.model;

import java.util.ArrayList;
import java.util.List;

import refactored.entities.interactions.Like;
import refactored.entities.interactions.Message;
import refactored.entities.interactions.Follow;
import refactored.entities.interactions.Notification;

public class NotificationQuery
{
    private int receiverID;
    private ArrayList<Notification> notifications;

    public NotificationQuery(int id)
    {
        LikeDBManager.retrieveLikes();
        FollowDBManager.retrieveFollows();
        MessageDBManager.retrieveMessages();
        
        this.receiverID = id;
        LikeDBManager.UserReceivedLikes likes = new LikeDBManager.UserReceivedLikes(receiverID);
        FollowDBManager.UserReceivedFollows follows = new FollowDBManager.UserReceivedFollows(receiverID);
        MessageDBManager.UserReceivedMessages messages = new MessageDBManager.UserReceivedMessages(receiverID);

        // linear sort
        notifications = new ArrayList<>();
        for(Like l : likes)
        {
            notifications.add(l);
        }

        for(Follow f : follows)
        {
            notifications.add(f);
        }
        
        for(Message m : messages)
        {
            notifications.add(m);
        }

        notifications.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
    }

    public List<Notification> getNotifications() { return notifications; }
}