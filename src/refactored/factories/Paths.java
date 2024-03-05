package refactored.factories;
import java.nio.file.Path;

public abstract class Paths
{
    //TODO: Change the paths to the correct ones, rework model
    //root path
    public static final Path rootPath = Path.of("src/refactored");
    //data path
    public static final Path dataPath = Path.of(rootPath.toString(), "/data");
    //profile pictures
    public static final Path profilePicturesPath = Path.of(dataPath.toString(), "/profile_pictures");

    //temp
    public static final Path tempImgDetailsPath = Path.of(dataPath.toString(), "img/image_details.txt");
    public static final Path tempFollowingPath = Path.of(dataPath.toString(), "/following.txt");
    public static final Path tempCredentialsPath = Path.of(dataPath.toString(), "/credentials.txt");
    public static final Path tempUsersPath = Path.of(dataPath.toString(), "/users.txt");
    public static final Path tempImgUploadedPath = Path.of(dataPath.toString(), "/img/uploaded");
    public static final Path tempProfilePicturesPath = Path.of(dataPath.toString(), "/img/storage/profile/");

    //icons
    public static final Path iconsPath = Path.of(dataPath.toString(), "/icons");
    public static final Path logoPath = Path.of(iconsPath.toString(), "/DACS.png");
    public static final Path homeIconPath = Path.of(iconsPath.toString(), "/home.png");
    public static final Path searchIconPath = Path.of(iconsPath.toString(), "/search.png");
    public static final Path addIconPath = Path.of(iconsPath.toString(), "/add.png");
    public static final Path heartIconPath = Path.of(iconsPath.toString(), "/heart.png");
    public static final Path profileIconPath = Path.of(iconsPath.toString(), "/profile.png");

    //messages
    public static final Path messagesPath = Path.of(dataPath.toString(), "/messages.json");

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