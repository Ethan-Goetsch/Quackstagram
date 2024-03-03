package Refactored;

import java.time.LocalDateTime;
import java.util.List;

public record Comment(User user, Content content, LocalDateTime DateCreated, List<Comment> Comments)
{

}