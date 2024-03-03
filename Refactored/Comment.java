package Refactored;

import java.time.LocalDateTime;

public record Comment(User user, IContent content, LocalDateTime DateCreated)
{

}