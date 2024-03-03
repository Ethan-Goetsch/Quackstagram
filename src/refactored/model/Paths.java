package refactored.model;
import java.nio.file.Path;

public abstract class Paths
{
    //TODO: Change the paths to the correct ones, rework model
    public static final Path rootPath = Path.of("src/refactored");
    public static final Path dataPath = Path.of(rootPath.toString(), "/data");
    public static final Path imgPath = Path.of(rootPath.toString(), "/img");
    public static final Path imgCopyPath = Path.of(imgPath.toString(), "/pictures");

    public static final Path iconsPath = Path.of(rootPath.toString(), "/img/icons/");
    public static final Path logosPath = Path.of(rootPath.toString(), "/img/logos");
    public static final Path profilePicturePath = Path.of(rootPath.toString(),"/img/storage/profile");
    
    public static final Path followingPath = Path.of(rootPath.toString(), "/following.json");
    public static final Path notificationsPath = Path.of(rootPath.toString(), "/notifications.json");
    public static final Path messagesPath = Path.of(rootPath.toString(), "/messages.json");

    //pictures
    public static final Path picturesPath = Path.of(imgPath.toString(), "pictures", "/pictures.json");
    public static final Path imageDetailsPath = Path.of(imgPath.toString(), "/details");

    //data
    public static final Path userCredentialsPath = Path.of(dataPath.toString(), "/userCredentials.json");
    public static final Path userAccountsPath = Path.of(dataPath.toString(), "/userAccounts.json");


    public static void main(String[] args)
    {
        System.out.println(userCredentialsPath.toString());
    }
}