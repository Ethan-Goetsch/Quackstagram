package refactored.factories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import refactored.entities.Content;

//currently, picture factory functionality is implemente in PictureDBManager.savePicture() // maybe move it here
public abstract class PictureFactory {
    
    public static Content createPicture(Path imagePath) {
        // give the picture a unique id
        //PictureDBManager.getNextID();
        return null;
    }

    //probably should not be here
    private static void copy(Path from, Path to) {
        // Create the file if it doesn't exist
        try
        {
            if (!to.toFile().exists())
            {
                to.toFile().createNewFile();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // Copy the file
        try
        {
            Files.copy(from, to);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
