package refactored.model;

import java.util.ArrayList;

import refactored.entities.interactions.Message;
import refactored.factories.Paths;

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
