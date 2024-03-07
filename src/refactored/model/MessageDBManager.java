package refactored.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import refactored.entities.interactions.Message;
import refactored.factories.Paths;

public class MessageDBManager extends DBManager<Message>
{
    private static ArrayList<Message> messages;

    public static void main(String[] args)
    {
        storeMessages();
    }

    public static void createMessage(int senderID, int receiverID, String message)
    {
        retrieveMessages();
        messages.add(new Message(generateID(), senderID, receiverID, message, LocalDateTime.now()));
        storeMessages();
    }

    private static int generateID()
    {
        retrieveMessages();
        int max = 0;
        for(Message m : messages)
        {
            if(m.getID() > max)
            {
                max = m.getID();
            }
        }
        return ++max;
    }

    public static class UserMessages implements Iterable<Message>
    {
        private int userID;
        private ArrayList<Message> messages;

        public UserMessages(int userID)
        {
            retrieveMessages();

            this.userID = userID;
            this.messages = new ArrayList<Message>();
            for(Message m : messages)
            {
                if(m.getSenderID() == userID || m.getReceiverID() == userID)
                {
                    this.messages.add(m);
                }
            }
        }

        public Iterator<Message> iterator()
        {
            return messages.iterator();
        }
    }

    public static class UserReceivedMessages implements Iterable<Message>
    {
        private int userID;
        private ArrayList<Message> messages;

        public UserReceivedMessages(int userID)
        {
            retrieveMessages();

            this.userID = userID;
            this.messages = new ArrayList<Message>();
            for(Message m : messages)
            {
                if(m.getReceiverID() == userID)
                {
                    this.messages.add(m);
                }
            }
        }

        public Iterator<Message> iterator()
        {
            return messages.iterator();
        }
    }


    public static void retrieveMessages() {
        if (messages != null)
            messages = retrieve(Paths.messagesDBPath);
    }
    public static void storeMessages() {
        store(messages, Paths.messagesDBPath);
    }
}
