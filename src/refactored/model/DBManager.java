package refactored.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;

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
