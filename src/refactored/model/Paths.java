package refactored.model;
import java.nio.file.Path;

public abstract class Paths
{
    //TODO: Change the paths to the correct ones, rework model

    public static final Path iconsPath = Path.of("/img/icons/");
    public static final Path logosPath = Path.of("/img/logos");
    public static final Path profilePicturePath = Path.of("/img/storage/profile");
    public static final Path uploadedImagePath = Path.of("/img/uploaded");
    public static final Path imageDetailsPath = Path.of("/img/details");

    public static final Path credentialsPath = Path.of("/data/credentials.json");
    public static final Path usersPath = Path.of("/data/users.json");

    public static final Path notificationsPath = Path.of("/data/notifications.json");
    public static final Path followingPath = Path.of("/data/following.json");
    public static final Path messagesPath = Path.of("/data/messages.json");    
}