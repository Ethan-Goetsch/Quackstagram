package refactored.model;
import java.io.Serializable;

import refactored.stored.UserAccount;

public class PostDBManager
{
    class Picture implements Serializable
    {
        int id;
        String caption;
        String fileName;
    }

    class Notification implements Serializable
    {
        int id;
        String message;
        UserAccount sender;
        UserAccount receiver;
    }

    class Message implements Serializable
    {
        int id;
        String message;
        UserAccount sender;
        UserAccount receiver;
    }

    class MessageGroup
    {
        int id;
        String name;
        UserAccount[] members;
        Message[] messages;
    }
}
