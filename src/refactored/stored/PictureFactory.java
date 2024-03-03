package refactored.stored;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import refactored.model.PictureDBManager;

public abstract class PictureFactory {
    
    public static Picture createPicture(Path imagePath) {
        // give the picture a unique id
        PictureDBManager.getNextID();

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
