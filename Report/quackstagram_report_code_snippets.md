# Code Snippets

# Magic Strings

* * *

## Path Files

### Before

**Hard Coded Strings throughout the application**

```java
private boolean verifyCredentials(String username, String password) {
    try (BufferedReader reader = new BufferedReader(new FileReader("data/credentials.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] credentials = line.split(":");
            if (credentials[0].equals(username) && credentials[1].equals(password)) {
            String bio = credentials[2];
            // Create User object and save information
        newUser = new User(username, bio, password); // Assuming User constructor takes these parameters
        saveUserInformation(newUser);
    
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}
```

  

```java
    private void saveCredentials(String username, String password, String bio) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/credentials.txt", true))) {
            writer.write(username + ":" + password + ":" + bio);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

  

```java
   try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "notifications.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(";");
        if (parts[0].trim().equals(currentUsername)) {
            // Format the notification message
            String userWhoLiked = parts[1].trim();
            String imageId = parts[2].trim();
            String timestamp = parts[3].trim();
            String notificationMessage = userWhoLiked + " liked your picture - " + getElapsedTime(timestamp) + " ago";

            // Add the notification to the panel
            JPanel notificationPanel = new JPanel(new BorderLayout());
            notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
            JLabel notificationLabel = new JLabel(notificationMessage);
            notificationPanel.add(notificationLabel, BorderLayout.CENTER);
            
            // Add profile icon (if available) and timestamp
            // ... (Additional UI components if needed)

            contentPanel.add(notificationPanel);
        }
    }
}
```

##   

### Afer

**Class stores all the paths for the application**

```java
public class Paths
{
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
}
```

  

```java
public abstract class DBManager<T> {

    @SuppressWarnings("unchecked")
    protected static <T> ArrayList<T> retrieve(Path from)
    {
        // try-with-resources to auto-close the file
        try (FileInputStream fos = new FileInputStream(from.toFile()); 
        ObjectInputStream oos = new ObjectInputStream(fos))     
        {
            // if the file contains a null object, return an empty list
            ArrayList<T> temp = (ArrayList<T>) oos.readObject();
            if (temp != null)
            {
                return temp;
            }
            return new ArrayList<T>();
        } catch (Exception e)
        {
            //maybe do something here
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    protected static <T> void store(ArrayList<T> from, Path to)
    {
        // try-with-resources to auto-close the file
        try (FileOutputStream fos = new FileOutputStream(to.toFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            // if the object is null, write an empty list to file
            if(from == null)
            {
                from = new ArrayList<T>();
            }
            oos.writeObject(from);
        } catch (Exception e)
        {
            //maybe do something here
            e.printStackTrace();
        }
    }
}
```

  

### Rationale

1. **Enhances Maintainability**
    1. With dedicated paths for each data file it is easier to change any data files since there is only a single reference to it throughout the code base
2. **Clear Responsibility**
    1. A single class responsible for data paths helps maintain single responsibility throughout the application. The class has a single clear purpose.
3. **Scalability**
    1. As new data files are added it is clear and concise where they must be referenced

  

## Button Types

### Before

**Hard coded button strings and responses to those string**

```java
    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
 
        // Define actions based on button type
        if ("home".equals(buttonType)) {
            button.addActionListener(e -> openHomeUI());
        } else if ("profile".equals(buttonType)) {
            button.addActionListener(e -> openProfileUI());
        } else if ("notification".equals(buttonType)) {
            button.addActionListener(e -> notificationsUI());
        } else if ("explore".equals(buttonType)) {
            button.addActionListener(e -> exploreUI());
        }
        return button;
 
        
    }
```

  

### After

**Enum for button page type**

```java
    /**
     * the nav bar needs to know which frame it belongs to in order to close that frame on button click.
     * there are ways of getting the reference of the frame from the JPannel, but it has to attached first. 
     * @param this
     * @return
     */
    public static JPanel createNavigationPanel(JFrame topFrame, IAction<PageType> navigateAction)
    {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Buttons
        navigationPanel.add(createNavButton(navigateAction, PageType.HOME, Paths.homeIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.EXPLORE, Paths.searchIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.UPLOAD, Paths.addIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.NOTIFICATION, Paths.heartIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.PROFILE, Paths.profileIconPath, topFrame));

        return navigationPanel;
    }
```

### Rationale

1. **Enhances Robustness**
    1. Referencing a type instead of a string improves robustness by being resilient to name changes
    2. Due to a reference, the types can be debugged and tracked
        1. I.e. If a value is deleted, the compiler will warn against it
2. **Promotes Open for Extension Closed for Modification principle**
    1. The buttons functionality can be extended without modifying the creation of the button

  

# UI Element Factory

* * *

## Before

**UI classes contain duplicate code to create UI elements**

```java
    private JPanel createHeaderPanel() {
       
        // Header Panel (reuse from InstagramProfileUI or customize for home page)
         // Header with the Register label
         JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
         JLabel lblRegister = new JLabel(" Upload Image 🐥");
         lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
         lblRegister.setForeground(Color.WHITE); // Set the text color to white
         headerPanel.add(lblRegister);
         headerPanel.setPreferredSize(new Dimension(WIDTH, 40)); // Give the header a fixed height
         return headerPanel;
   }

   private JPanel createNavigationPanel() {
       // Create and return the navigation panel
        // Navigation Bar
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel.add(createIconButton("img/icons/home.png", "home"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/search.png","explore"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/add.png"," "));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/heart.png","notification"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/profile.png", "profile"));

        return navigationPanel;
   }

    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
 
        // Define actions based on button type
        if ("home".equals(buttonType)) {
            button.addActionListener(e -> openHomeUI());
        } else if ("profile".equals(buttonType)) {
            button.addActionListener(e -> openProfileUI());
        } else if ("notification".equals(buttonType)) {
            button.addActionListener(e -> notificationsUI());
        } else if ("explore".equals(buttonType)) {
            button.addActionListener(e -> exploreUI());
        }
        return button;
 
        
    }
```

## After

**Single UI Element Factory to create and return common UI elements**

```java
public class UIElementFactory
{
    private static final int NAV_ICON_SIZE = 20;

    public static JPanel createHeaderPanel(int width, String title)
    {
        // Header with the Register label
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(51, 51, 51)); // Set a darker background for the header
        JLabel lblRegister = new JLabel(title);
        lblRegister.setFont(new Font("Arial", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE); // Set the text color to white
        headerPanel.add(lblRegister);
        headerPanel.setPreferredSize(new Dimension(width, 40)); // Give the header a fixed height
        return headerPanel;
    }

    /**
     * the nav bar needs to know which frame it belongs to in order to close that frame on button click.
     * there are ways of getting the reference of the frame from the JPannel, but it has to attached first. 
     * @param this
     * @return
     */
    public static JPanel createNavigationPanel(JFrame topFrame, IAction<PageType> navigateAction)
    {
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Buttons
        navigationPanel.add(createNavButton(navigateAction, PageType.HOME, Paths.homeIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.EXPLORE, Paths.searchIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.UPLOAD, Paths.addIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.NOTIFICATION, Paths.heartIconPath, topFrame));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createNavButton(navigateAction, PageType.PROFILE, Paths.profileIconPath, topFrame));

        return navigationPanel;
    }

    private static JButton createNavButton(IAction<PageType> navigateAction, PageType pageType, Path iconPath, JFrame topFrame)
    {
        ImageIcon iconOriginal = new ImageIcon(iconPath.toString());
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.addActionListener(e -> navigateAction.execute(pageType));
        return button;
    }

    public static JScrollPane createImageGridPanel(int GRID_IMAGE_SIZE, Iterable<Post> posts, IAction2<Post, ImageIcon> imageClickedListener)
    {
        // panel to be decorated with scroll bar
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 3,2, 2)); // Grid layout for image grid

        // get user posts
        for (Post post : posts)
        {
            Path path = post.getFilePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            
            // Add a mouse listener to the image label
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    imageClickedListener.execute(post, imageIcon);
                }
            });
            contentPanel.add(imageLabel);
        }

        // scroll pane decorator
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        return scrollPane;
    }
}
```

  

## Rationale

1. **Promotes Single Responsibility**
    1. UI does not need to create its UI elements
2. **Enhances Scalability**
    1. Easily allows for implementing new features to all UI elements
        1. I.e. A Dark and Light mode for the UI visuals
            1. Abstract Factory ➝ Dark UI Element Factory | Light UI Element Factory
3. **Enhances Maintainability**
    1. A dedicated class for for UI element creation allows for easy modification to the creation of UI elements
        1. I.e. Changing the icon displayed for the profile profile
4. **Respects DRY**
    1. Reduces and removes duplicate code for creating common UI elements

# Model-View-Controller

* * *

## Coupled UI

### Before

**High Coupling**

**Each UI class instantiates every other UI class**

```java
 private void ImageUploadUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ImageUploadUI upload = new ImageUploadUI();
        upload.setVisible(true);
    }

    private void openProfileUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        InstagramProfileUI profileUI = new InstagramProfileUI();
        profileUI.setVisible(true);
    }
 
     private void notificationsUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        NotificationsUI notificationsUI = new NotificationsUI();
        notificationsUI.setVisible(true);
    }
 
    private void openHomeUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        QuakstagramHomeUI homeUI = new QuakstagramHomeUI();
        homeUI.setVisible(true);
    }
 
    private void exploreUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ExploreUI explore = new ExploreUI();
        explore.setVisible(true);
    }   
```

  

### After

**Loose**

**A single UI Manager handles navigation between UI and instantiates the relevant UI**

```java
public class UIManager
{
    private IPageController signInPage;
    private IPageController signUpPage;
    private IPageController homePage;
    private IPageController explorePage;
    private IPageController uploadPage;
    private IPageController notificationPage;
    private IPageController profilePage;

    private IPageController activePage;

    public void openSignUp()
    {
        signUpPage = new SignUpController(this);
        setActivePage(signUpPage);
    }

    public void openSignIn()
    {
        signInPage = new SignInController(this);
        setActivePage(signInPage);
    }

    public void openHome(int userId)
    {
        homePage = new HomeController(this);
        setActivePage(homePage);
    }

    public void openExplore(int userId)
    {
        OtherUsersPosts posts = new OtherUsersPosts(currentUserId);
        explorePage = new ExploreController(this, posts);
        setActivePage(explorePage);
    }

    private void openUpload(int userId)
    {
        uploadPage = new UploadController(this, userId, UserDBManager.getUsername(userId));
        setActivePage(uploadPage);
    }

    private void openNotifications(int userId)
    {
        notificationPage = new NotificationController(this, currentUserId);
        setActivePage(notificationPage);
    }

    public void openProfile(int userId)
    {
        profilePage = new ProfileController(this, userId, currentUserId == userId, FollowDBManager.isAFollowingB(currentUserId, userId), PostDBManager.getUserPosts(userId));
        setActivePage(profilePage);
    }

    public void navigateToPage(PageType page)
    {
        switch (page)
        {
            case EXPLORE:
                openExplore(currentUserId);
                break;
            case HOME:
                openHome(currentUserId);
                break;
            case NOTIFICATION:
                openNotifications(currentUserId);
                break;
            case PROFILE:
                openProfile(currentUserId);
                break;
            case UPLOAD:
                openUpload(currentUserId);
                break;
            default:
                break;
        }
    }
}
```

### Rationale

1. **Enhances Maintainability**
    1. Navigation between pages is handled in a single class, which allows for easy modification to the handling of the navigation
2. **Enhances Robustness**
    1. Loosely coupled classes allows for easy modification to a class without causing changes in other classes

##   

## Responsibility

### Before

**UI has no single responsibility. Controls Logic, Saving/Loading Data, Navigation and Interaction**

_(I hope the shear size of this class proves my point)_

```java
public class InstagramProfileUI extends JFrame {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private static final int NAV_ICON_SIZE = 20; // Corrected static size for bottom icons
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private JPanel headerPanel;   // Panel for the header
    private JPanel navigationPanel; // Panel for the navigation
    private User currentUser; // User object to store the current user's information

    public InstagramProfileUI(User user) {
        this.currentUser = user;
         // Initialize counts
        int imageCount = 0;
        int followersCount = 0;
        int followingCount = 0;
       
        // Step 1: Read image_details.txt to count the number of images posted by the user
    Path imageDetailsFilePath = Paths.get("img", "image_details.txt");
    try (BufferedReader imageDetailsReader = Files.newBufferedReader(imageDetailsFilePath)) {
        String line;
        while ((line = imageDetailsReader.readLine()) != null) {
            if (line.contains("Username: " + currentUser.getUsername())) {
                imageCount++;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Step 2: Read following.txt to calculate followers and following
    Path followingFilePath = Paths.get("data", "following.txt");
    try (BufferedReader followingReader = Files.newBufferedReader(followingFilePath)) {
        String line;
        while ((line = followingReader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String username = parts[0].trim();
                String[] followingUsers = parts[1].split(";");
                if (username.equals(currentUser.getUsername())) {
                    followingCount = followingUsers.length;
                } else {
                    for (String followingUser : followingUsers) {
                        if (followingUser.trim().equals(currentUser.getUsername())) {
                            followersCount++;
                        }
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    String bio = "";

    Path bioDetailsFilePath = Paths.get("data", "credentials.txt");
    try (BufferedReader bioDetailsReader = Files.newBufferedReader(bioDetailsFilePath)) {
        String line;
        while ((line = bioDetailsReader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts[0].equals(currentUser.getUsername()) && parts.length >= 3) {
                bio = parts[2];
                break; // Exit the loop once the matching bio is found
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    System.out.println("Bio for " + currentUser.getUsername() + ": " + bio);
    currentUser.setBio(bio);
    

    currentUser.setFollowersCount(followersCount);
    currentUser.setFollowingCount(followingCount);
    currentUser.setPostCount(imageCount);

    System.out.println(currentUser.getPostsCount());

     setTitle("DACS Profile");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        contentPanel = new JPanel();
        headerPanel = createHeaderPanel();       // Initialize header panel
        navigationPanel = createNavigationPanel(); // Initialize navigation panel

        initializeUI();
    }


      public InstagramProfileUI() {

        setTitle("DACS Profile");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        contentPanel = new JPanel();
        headerPanel = createHeaderPanel();       // Initialize header panel
        navigationPanel = createNavigationPanel(); // Initialize navigation panel
        initializeUI();
    }

    private void initializeUI() {
        getContentPane().removeAll(); // Clear existing components
        
        // Re-add the header and navigation panels
        add(headerPanel, BorderLayout.NORTH);
        add(navigationPanel, BorderLayout.SOUTH);

        // Initialize the image grid
        initializeImageGrid();

        revalidate();
        repaint();
    }

    private JPanel createHeaderPanel() {
        boolean isCurrentUser = false;
        String loggedInUsername = "";

        // Read the logged-in user's username from users.txt
    try (BufferedReader reader = Files.newBufferedReader(Paths.get("data", "users.txt"))) {
        String line = reader.readLine();
        if (line != null) {
            loggedInUsername = line.split(":")[0].trim();
            isCurrentUser = loggedInUsername.equals(currentUser.getUsername());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    
       // Header Panel
        JPanel headerPanel = new JPanel();
        try (Stream<String> lines = Files.lines(Paths.get("data", "users.txt"))) {
            isCurrentUser = lines.anyMatch(line -> line.startsWith(currentUser.getUsername() + ":"));
        } catch (IOException e) {
            e.printStackTrace();  // Log or handle the exception as appropriate
        }

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);
        
        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile image
        ImageIcon profileIcon = new ImageIcon(new ImageIcon("img/storage/profile/"+currentUser.getUsername()+".png").getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user"+currentUser.getPostsCount());
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getPostsCount()) , "Posts"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowersCount()), "Followers"));
        statsPanel.add(createStatLabel(Integer.toString(currentUser.getFollowingCount()), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding

        
// Follow Button
// Follow or Edit Profile Button
// followButton.addActionListener(e -> handleFollowAction(currentUser.getUsername()));
JButton followButton;
    if (isCurrentUser) {
        followButton = new JButton("Edit Profile");
    } else {
        followButton = new JButton("Follow");

        // Check if the current user is already being followed by the logged-in user
        Path followingFilePath = Paths.get("data", "following.txt");
        try (BufferedReader reader = Files.newBufferedReader(followingFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].trim().equals(loggedInUsername)) {
                    String[] followedUsers = parts[1].split(";");
                    for (String followedUser : followedUsers) {
                        if (followedUser.trim().equals(currentUser.getUsername())) {
                            followButton.setText("Following");
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        followButton.addActionListener(e -> {
            handleFollowAction(currentUser.getUsername());
            followButton.setText("Following");
        });
    }
    
followButton.setAlignmentX(Component.CENTER_ALIGNMENT);
followButton.setFont(new Font("Arial", Font.BOLD, 12));
followButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, followButton.getMinimumSize().height)); // Make the button fill the horizontal space
followButton.setBackground(new Color(225, 228, 232)); // A soft, appealing color that complements the UI
followButton.setForeground(Color.BLACK);
followButton.setOpaque(true);
followButton.setBorderPainted(false);
followButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some vertical padding

        
        // Add Stats and Follow Button to a combined Panel
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));
        statsFollowPanel.add(statsPanel);
        statsFollowPanel.add(followButton);
        topHeaderPanel.add(statsFollowPanel, BorderLayout.CENTER);

        headerPanel.add(topHeaderPanel);

     // Profile Name and Bio Panel
JPanel profileNameAndBioPanel = new JPanel();
profileNameAndBioPanel.setLayout(new BorderLayout());
profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

JLabel profileNameLabel = new JLabel(currentUser.getUsername());
profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides

JTextArea profileBio = new JTextArea(currentUser.getBio());
System.out.println("This is the bio "+currentUser.getUsername());
profileBio.setEditable(false);
profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
profileBio.setBackground(new Color(249, 249, 249));
profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides

profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

headerPanel.add(profileNameAndBioPanel);


        
        return headerPanel;

    }


   private void handleFollowAction(String usernameToFollow) {
    Path followingFilePath = Paths.get("data", "following.txt");
    Path usersFilePath = Paths.get("data", "users.txt");
    String currentUserUsername = "";

    try {
        // Read the current user's username from users.txt
        try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
               currentUserUsername = parts[0];
            }
        }

        System.out.println("Real user is "+currentUserUsername);
        // If currentUserUsername is not empty, process following.txt
        if (!currentUserUsername.isEmpty()) {
            boolean found = false;
            StringBuilder newContent = new StringBuilder();

            // Read and process following.txt
            if (Files.exists(followingFilePath)) {
                try (BufferedReader reader = Files.newBufferedReader(followingFilePath)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts[0].trim().equals(currentUserUsername)) {
                            found = true;
                            if (!line.contains(usernameToFollow)) {
                                line = line.concat(line.endsWith(":") ? "" : "; ").concat(usernameToFollow);
                            }
                        }
                        newContent.append(line).append("\n");
                    }
                }
            }

            // If the current user was not found in following.txt, add them
            if (!found) {
                newContent.append(currentUserUsername).append(": ").append(usernameToFollow).append("\n");
            }

            // Write the updated content back to following.txt
            try (BufferedWriter writer = Files.newBufferedWriter(followingFilePath)) {
                writer.write(newContent.toString());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    

    
    
    private JPanel createNavigationPanel() {
        // Navigation Bar
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(249, 249, 249));
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        navigationPanel.add(createIconButton("img/icons/home.png", "home"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/search.png","explore"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/add.png","add"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/heart.png","notification"));
        navigationPanel.add(Box.createHorizontalGlue());
        navigationPanel.add(createIconButton("img/icons/profile.png", "profile"));

        return navigationPanel;

    }

private void initializeImageGrid() {
    contentPanel.removeAll(); // Clear existing content
    contentPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Grid layout for image grid

    Path imageDir = Paths.get("img", "uploaded");
    try (Stream<Path> paths = Files.list(imageDir)) {
        paths.filter(path -> path.getFileName().toString().startsWith(currentUser.getUsername() + "_"))
             .forEach(path -> {
                 ImageIcon imageIcon = new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(GRID_IMAGE_SIZE, GRID_IMAGE_SIZE, Image.SCALE_SMOOTH));
                 JLabel imageLabel = new JLabel(imageIcon);
                 imageLabel.addMouseListener(new MouseAdapter() {
                     @Override
                     public void mouseClicked(MouseEvent e) {
                         displayImage(imageIcon); // Call method to display the clicked image
                     }
                 });
                 contentPanel.add(imageLabel);
             });
    } catch (IOException ex) {
        ex.printStackTrace();
        // Handle exception (e.g., show a message or log)
    }

    JScrollPane scrollPane = new JScrollPane(contentPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center

    revalidate();
    repaint();
}



    private void displayImage(ImageIcon imageIcon) {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            getContentPane().removeAll(); // Remove all components from the frame
            initializeUI(); // Re-initialize the UI
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }



    private JLabel createStatLabel(String number, String text) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JButton createIconButton(String iconPath, String buttonType) {
        ImageIcon iconOriginal = new ImageIcon(iconPath);
        Image iconScaled = iconOriginal.getImage().getScaledInstance(NAV_ICON_SIZE, NAV_ICON_SIZE, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(iconScaled));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
    
        // Define actions based on button type
        if ("home".equals(buttonType)) {
            button.addActionListener(e -> openHomeUI());
        } else if ("profile".equals(buttonType)) {
            //
        } else if ("notification".equals(buttonType)) {
            button.addActionListener(e -> notificationsUI());
        } else if ("explore".equals(buttonType)) {
            button.addActionListener(e -> exploreUI());
        } else if ("add".equals(buttonType)) {
            button.addActionListener(e -> ImageUploadUI());
        }
        return button;
    
        
    }
 
    private void ImageUploadUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ImageUploadUI upload = new ImageUploadUI();
        upload.setVisible(true);
    }

    private void openProfileUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        InstagramProfileUI profileUI = new InstagramProfileUI();
        profileUI.setVisible(true);
    }
 
     private void notificationsUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        NotificationsUI notificationsUI = new NotificationsUI();
        notificationsUI.setVisible(true);
    }
 
    private void openHomeUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        QuakstagramHomeUI homeUI = new QuakstagramHomeUI();
        homeUI.setVisible(true);
    }
 
    private void exploreUI() {
        // Open InstagramProfileUI frame
        this.dispose();
        ExploreUI explore = new ExploreUI();
        explore.setVisible(true);
    }   

    
}
```

  

### After

**UI has been separated into a Model, View and Controller. Each with a single responsibility**

_Model is omitted due to being saved in text files and accessed by multiple Database classes_

```java
public class ProfileController implements IPageController
{
    private final UIManager manager;
    private final ProfilePage page;

    private final int userID;
    private final boolean isOwner;

    public ProfileController(UIManager uiManager, int userID, boolean isOwner, boolean isFollowing, List<Post> posts)
    {
        this.manager = uiManager;
        this.userID = userID;
        this.page = new ProfilePage(userID, isOwner, isFollowing, id -> handleFollowOrEditAction(id), pageType -> manager.navigateToPage(pageType), posts);
        this.isOwner = isOwner;
    }

    private void handleFollowOrEditAction(int id)
    {
        if (isOwner)
            handleEdit(id);
        else
            handleFollow(id);
    }

    private void handleFollow(int userIdToFollow)
    {
        FollowDBManager.createFollow(userID, userIdToFollow);

        boolean isFollowing = FollowDBManager.isAFollowingB(manager.getCurrentUserId(), userIdToFollow);
        int followees = FollowDBManager.getFolloweeCount(userIdToFollow);

        page.updateEditOrFollowButtonLabel(isFollowing ? "Unfollow" : "Follow");
        page.updateFolloweeCount(followees); // followers actually retrieved in updateFollowerCount
    }

    private void handleEdit(int userId)
    {
        
    }

    @Override
    public void open()
    {
        page.setVisible(true);
    }

    @Override
    public void close()
    {
        page.dispose();
    }
}

public class ProfilePage extends JFrame
{
    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;
    private static final int GRID_IMAGE_SIZE = WIDTH / 3; // Static size for grid images
    private static final int PROFILE_IMAGE_SIZE = 80; // Adjusted size for the profile image to match UI
    
    private final boolean isOwner;
    private final int userId;

    private final IAction<Integer> followAction;
    private final IAction<PageType> navigateAction;

    private final List<Post> posts;

    private JPanel headerPanel;   // Panel for the header
    private JPanel contentPanel; // Panel to display the image grid or the clicked image
    private JPanel navigationPanel; // Panel for the navigation

    private JButton editOrFollowButton;
    private JLabel followersLabel;

    private boolean isFollowing;

    public ProfilePage(int userId, boolean isOwner, boolean isFollowing, IAction<Integer> followAction, IAction<PageType> navigateAction, List<Post> posts)
    {
        this.userId = userId;
        this.isOwner = isOwner;
        this.isFollowing = isFollowing;

        this.followAction = followAction;
        this.navigateAction = navigateAction;

        this.posts = posts;

        setTitle("DACS Profile");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI()
    {
        getContentPane().removeAll(); // Clear existing components
        
        headerPanel = createProfileHeader(); // state-dependent : either follow button or edit profile button

        /// content grid //TODO : Make selected images fullscreen
        JScrollPane contentScrollPane = UIElementFactory.createImageGridPanel(GRID_IMAGE_SIZE, posts, (post, imageIcon) -> displayImage(post, imageIcon));
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);
        navigationPanel = UIElementFactory.createNavigationPanel(this, navigateAction); // state-independent : always opens the current user profile

        // Re-add the header and navigation panels
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void displayImage(Post post, ImageIcon imageIcon)
    {
        contentPanel.removeAll(); // Remove existing content
        contentPanel.setLayout(new BorderLayout()); // Change layout for image display

        // Add the full-size image to the panel
        JLabel fullSizeImageLabel = new JLabel(imageIcon);
        fullSizeImageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(fullSizeImageLabel, BorderLayout.CENTER);

        // Add a back button to return to the grid view
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e ->
        {
            getContentPane().removeAll(); // Remove all components from the frame
            initializeUI(); // Re-initialize the UI
        });
        contentPanel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JPanel createProfileHeader()
    {
        // profile's user account
        String username = UserDBManager.getUsername(userId);
        String bio = UserDBManager.getBio(userId);
        int postsCount = PostDBManager.getPostCount(userId);
        int followingCount = FollowDBManager.getFollowerCount(userId);
        int followeeCount = FollowDBManager.getFolloweeCount(userId);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Color.GRAY);
        
        // Top Part of the Header (Profile Image, Stats, Follow Button)
        JPanel topHeaderPanel = new JPanel(new BorderLayout(10, 0));
        topHeaderPanel.setBackground(new Color(249, 249, 249));

        // Profile image
        ImageIcon profileIcon = new ImageIcon(new ImageIcon(Path.of(Paths.profilePicturesPath.toString(), username + ".png").toString()).getImage().getScaledInstance(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, Image.SCALE_SMOOTH));
        JLabel profileImage = new JLabel(profileIcon);
        profileImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topHeaderPanel.add(profileImage, BorderLayout.WEST);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        statsPanel.setBackground(new Color(249, 249, 249));
        System.out.println("Number of posts for this user " + postsCount);
        statsPanel.add(createStatLabel(Integer.toString(postsCount) , "Posts"));
        followersLabel = createStatLabel(Integer.toString(followeeCount), "Followers");
        statsPanel.add(followersLabel);
        statsPanel.add(createStatLabel(Integer.toString(followingCount), "Following"));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0)); // Add some vertical padding
        
        // Follow or Edit Profile Button (depending on the user)
        editOrFollowButton = new JButton(isOwner ? "Edit Profile" : isFollowing ? "Unfollow" : "Follow");
        editOrFollowButton.addActionListener(e -> followAction.execute(userId));
        
        editOrFollowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editOrFollowButton.setFont(new Font("Arial", Font.BOLD, 12));
        editOrFollowButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, editOrFollowButton.getMinimumSize().height)); // Make the button fill the horizontal space
        editOrFollowButton.setBackground(new Color(225, 228, 232)); // A soft, appealing color that complements the UI
        editOrFollowButton.setForeground(Color.BLACK);
        editOrFollowButton.setOpaque(true);
        editOrFollowButton.setBorderPainted(false);
        editOrFollowButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add some vertical padding

        // Add Stats and Follow Button to a combined Panel
        JPanel statsFollowPanel = new JPanel();
        statsFollowPanel.setLayout(new BoxLayout(statsFollowPanel, BoxLayout.Y_AXIS));
        statsFollowPanel.add(statsPanel);
        statsFollowPanel.add(editOrFollowButton);
        topHeaderPanel.add(statsFollowPanel, BorderLayout.CENTER);

        headerPanel.add(topHeaderPanel);

        // Profile Name and Bio Panel
        JPanel profileNameAndBioPanel = new JPanel();
        profileNameAndBioPanel.setLayout(new BorderLayout());
        profileNameAndBioPanel.setBackground(new Color(249, 249, 249));

        JLabel profileNameLabel = new JLabel(username);
        profileNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profileNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding on the sides

        JTextArea profileBio = new JTextArea(bio);
        System.out.println("This is the bio "+username + " " + userId);
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Arial", Font.PLAIN, 12));
        profileBio.setBackground(new Color(249, 249, 249));
        profileBio.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding on the sides

        profileNameAndBioPanel.add(profileNameLabel, BorderLayout.NORTH);
        profileNameAndBioPanel.add(profileBio, BorderLayout.CENTER);

        headerPanel.add(profileNameAndBioPanel);

        return headerPanel;
    }

    private JLabel createStatLabel(String number, String text)
    {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + number + "<br/>" + text + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    public void updateEditOrFollowButtonLabel(String string)
    {
        editOrFollowButton.setText(string);
    }

    public void updateFolloweeCount(int followeeCount)
    {
        followersLabel.setText("<html><div style='text-align: center;'>" + followeeCount + "<br/>" + "Followers" + "</div></html>");
    }
}
```

### Rationale

1. **Promotes Single Responsibility principle**
    1. Each class has a single responsibility
2. **Clarity**
    1. Easier to understand the purpose of each class and maintain code readability
3. **Enhances Modularity**
    1. Splitting a large UI class into smaller parts allows for those parts to be reused elsewhere