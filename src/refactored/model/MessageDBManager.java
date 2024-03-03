package refactored.model;

import java.util.ArrayList;

import refactored.data.Paths;
import refactored.entities.interactions.Message;

public class MessageDBManager extends DBManager<Message>
{
    private static ArrayList<Message> messages;

    public static void main(String[] args)
    {
        storeMessages();
    }

    public static void storeMessages() { store(messages, Paths.messagesPath); }
    public static void retrieveMessages() { messages = retrieve(Paths.messagesPath); }
}
