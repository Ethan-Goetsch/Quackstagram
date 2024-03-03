package refactored.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import refactored.data.Paths;
import refactored.entities.Content;

/**
 * Manages the contents in the database
 *  // TODO: 12 years ago: fix it so that contents can't be overridden if they have the same name
 */
public abstract class ContentDBManager extends DBManager<Content> {
    private static ArrayList<Content> contents;

    public static void main(String[] args)
    {
        // test
        saveContent(Path.of(Paths.imgPath.toString(), "logos/DACS.png"));
        storeContents();

        contents = null;
        retrieveContents();
    }

    /**
     * Saves a content to the database by copying it to the contents folder
     * @param contentPath
     */
    public static void saveContent(Path contentPath) {
        retrieveContents();

        // copy the content to the contents folder
        Path copyContentPath = Path.of(Paths.imgCopyPath.toString(), contentPath.getFileName().toString());
        
        // copy logic
        try
        {
            Files.copy(contentPath, copyContentPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // content saving logic
        Content content = new Content(findMaxID(contents)+1, copyContentPath.getFileName().toString());
        contents.add(content);

        storeContents();
    }

    private static int findMaxID(ArrayList<Content> contents)
    {
        int max = 0;
        for(Content p : contents)
        {
            if(p.id() > max)
            {
                max = p.id();
            }
        }
        return max;
    }

    private static void retrieveContents()
    {
        if(contents == null)
            contents = retrieve(Paths.contentsPath);
    }
    public static void storeContents() { store(contents, Paths.contentsPath); }
}
