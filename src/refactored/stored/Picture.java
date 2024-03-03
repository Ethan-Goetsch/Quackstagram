package refactored.stored;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

// Represents a picture on Quackstagram
public record Picture(String fileName, int id) implements Serializable
{
    public Picture
    {
        Objects.requireNonNull(fileName, "fileName must not be null");
        Objects.requireNonNull(id, "id must not be null");
    }
}