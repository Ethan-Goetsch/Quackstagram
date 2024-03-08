package refactored.model;

import java.util.ArrayList;
import java.util.Iterator;

import refactored.entities.interactions.Like;
import refactored.entities.interactions.Message;
import refactored.entities.interactions.Follow;
import refactored.entities.interactions.Notification;

public class NotificationQuery {
    public static class NotificationQueryResult implements Iterable<Notification>
    {
        private int receiverID;
        private ArrayList<Notification> notifications;


        public static void main(String[] args) {
            NotificationQueryResult nqr = new NotificationQueryResult(4);
            nqr.print();
        }

        private void print() {
            for(Notification n : notifications) {
                System.out.println(n.getNotificationMessage());
            }
        }

        public NotificationQueryResult(int id) {
            LikeDBManager.retrieveLikes();
            FollowDBManager.retrieveFollows();
            MessageDBManager.retrieveMessages();
            
            this.receiverID = id;
            LikeDBManager.UserReceivedLikes likes = new LikeDBManager.UserReceivedLikes(receiverID);
            FollowDBManager.UserReceivedFollows follows = new FollowDBManager.UserReceivedFollows(receiverID);
            MessageDBManager.UserReceivedMessages messages = new MessageDBManager.UserReceivedMessages(receiverID);

            // linear sort
            notifications = new ArrayList<>();
            for(Like l : likes) {
                notifications.add(l);
            }

            for(Follow f : follows) {
                notifications.add(f);
            }
            
            for(Message m : messages) {
                notifications.add(m);
            }

            notifications.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
        }

        private class NotificationIterator implements Iterator<Notification>
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < notifications.size();
            }

            @Override
            public Notification next()
            {
                return notifications.get(index++);
            }
        }

        @Override
        public Iterator<Notification> iterator()
        {
            return new NotificationIterator();
        }
    }
}
