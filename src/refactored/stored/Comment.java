package refactored.stored;

import java.time.LocalDateTime;
import java.util.List;

import refactored.User;

public record Comment(User user, Content content, LocalDateTime DateCreated, List<Comment> Comments)
{

}