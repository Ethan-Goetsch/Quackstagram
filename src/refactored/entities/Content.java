package refactored.entities;

import java.io.Serializable;
import java.util.Objects;

// Represents a picture on Quackstagram
public record Content(int id, String fileName) implements Serializable
{
    public Content
    {
        Objects.requireNonNull(fileName, "fileName must not be null");
        Objects.requireNonNull(id, "id must not be null");
    }
}