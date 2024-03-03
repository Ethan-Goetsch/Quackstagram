package refactored.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import refactored.stored.Picture;

/**
 * Manages the pictures in the database
 *  // TODO: 12 years ago: fix it so that pictures can't be overridden if they have the same name
 */
public abstract class PictureDBManager extends DBManager<Picture> {
    private static ArrayList<Picture> pictures;

    public static void main(String[] args)
    {
        // test
        savePicture(Path.of(Paths.imgPath.toString(), "logos/DACS.png"));
        storePictures();

        pictures = null;
        retrievePictures();
    }

    /**
     * Saves a picture to the database by copying it to the pictures folder
     * @param picturePath
     */
    public static void savePicture(Path picturePath) {
        retrievePictures();

        // copy the picture to the pictures folder
        Path copyPicturePath = Path.of(Paths.imgCopyPath.toString(), picturePath.getFileName().toString());
        
        // copy logic
        try
        {
            Files.copy(picturePath, copyPicturePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // picture saving logic
        Picture picture = new Picture(copyPicturePath.getFileName().toString(), findMaxID(pictures)+1);
        pictures.add(picture);

        storePictures();
    }

    private static int findMaxID(ArrayList<Picture> pictures)
    {
        int max = 0;
        for(Picture p : pictures)
        {
            if(p.id() > max)
            {
                max = p.id();
            }
        }
        return max;
    }

    private static void retrievePictures()
    {
        if(pictures == null)
            pictures = retrieve(Paths.picturesPath);
    }

    public static void storePictures()
    {
        store(pictures, Paths.picturesPath);
    }
}
