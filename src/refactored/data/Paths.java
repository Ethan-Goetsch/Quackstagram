package refactored.data;
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

    //comments
    public static final Path commentsPath = Path.of(dataPath.toString(), "/comments.json");

    //follows
    public static final Path followsPath = Path.of(dataPath.toString(), "/follows.json");

    //likes
    public static final Path likesPath = Path.of(dataPath.toString(), "/likes.json");

    //posts
    public static final Path postsPath = Path.of(dataPath.toString(), "/posts.json");

    //content
    public static final Path contentsPath = Path.of(dataPath.toString(), "contents", "/contents.json");

    //data
    public static final Path usersPath = Path.of(dataPath.toString(), "/users.json");


    public static void main(String[] args)
    {
        System.out.println(usersPath.toString());
    }
}