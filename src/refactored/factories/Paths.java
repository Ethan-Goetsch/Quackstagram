package refactored.factories;
import java.nio.file.Path;

public abstract class Paths
{
    // TODO: Change the paths to the correct ones, rework model
    // root path
    public static final Path rootPath = Path.of("src/refactored/");
    // data path
    public static final Path dataPath = Path.of("data/");
    // contents
    public static final Path contentsPath = Path.of(dataPath.toString(), "contents/");
    // profile pictures
    public static final Path profilePicturesPath = Path.of(contentsPath.toString(), "/profile_pictures/");
    // uploads
    public static final Path uploadsPath = Path.of(contentsPath.toString(), "/uploads/");

    /* //temp
    public static final Path tempImgDetailsPath = Path.of(dataPath.toString(), "img/image_details.txt");
    public static final Path tempFollowingPath = Path.of(dataPath.toString(), "/following.txt");
    public static final Path tempCredentialsPath = Path.of(dataPath.toString(), "/credentials.txt");
    public static final Path tempUsersPath = Path.of(dataPath.toString(), "/users.txt");
    public static final Path tempImgUploadedPath = Path.of(dataPath.toString(), "/img/uploaded");
    public static final Path tempProfilePicturesPath = Path.of(dataPath.toString(), "/img/storage/profile/");
    */
    //icons
    public static final Path iconsPath = Path.of(dataPath.toString(), "/icons");
    public static final Path logoPath = Path.of(iconsPath.toString(), "/DACS.png");
    public static final Path homeIconPath = Path.of(iconsPath.toString(), "/home.png");
    public static final Path searchIconPath = Path.of(iconsPath.toString(), "/search.png");
    public static final Path addIconPath = Path.of(iconsPath.toString(), "/add.png");
    public static final Path heartIconPath = Path.of(iconsPath.toString(), "/heart.png");
    public static final Path profileIconPath = Path.of(iconsPath.toString(), "/profile.png");

    //content
    public static final Path contentsDBPath = Path.of(contentsPath.toString(), "/contents.json");
    //posts
    public static final Path postsDBPath = Path.of(contentsPath.toString(), "/posts.json");
    //messages
    public static final Path messagesDBPath = Path.of(dataPath.toString(), "/messages.json");
    //comments
    public static final Path commentsDBPath = Path.of(dataPath.toString(), "/comments.json");
    //follows
    public static final Path followsDBPath = Path.of(dataPath.toString(), "/follows.json");
    //likes
    public static final Path likesDBPath = Path.of(dataPath.toString(), "/likes.json");
    //data
    public static final Path usersDBPath = Path.of(dataPath.toString(), "/users.json");


    public static void main(String[] args)
    {
    }
}