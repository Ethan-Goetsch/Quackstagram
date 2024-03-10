package refactored.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import refactored.Paths;
import refactored.entities.interactions.Message;

public class MessageDBManager extends DBManager<Message>
{
    private static ArrayList<Message> messages;

    public static void main(String[] args)
    {
        // init
        messages = new ArrayList<>();
        messages.add(new Message(1, 3, 1, "I like your ears!", LocalDateTime.now()));
        storeMessages();

        // test
        messages = null;
        retrieveMessages();
        print();
    }

    private static void print()
    {
        for(Message m : messages)
        {
            System.out.println(m.getSenderID() + " " + m.getMessage() + " " + m.getReceiverID());
        }
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
        private ArrayList<Message> userMessages;

        public UserReceivedMessages(int userID)
        {
            retrieveMessages();

            this.userID = userID;
            this.userMessages = new ArrayList<Message>();
            for(Message m : messages)
            {
                if(m.getReceiverID() == userID)
                {
                    this.userMessages.add(m);
                }
            }
        }

        public Iterator<Message> iterator()
        {
            return userMessages.iterator();
        }
    }


    public static void retrieveMessages() {
        if (messages == null)
            messages = retrieve(Paths.messagesDBPath);
    }
    public static void storeMessages() {
        store(messages, Paths.messagesDBPath);
    }
}
