package Refactored;

import java.time.LocalDateTime;
import java.util.List;

public record Post(User Creator, Picture Picture, LocalDateTime DateCreated, String Caption, List<User> LikedUsers, List<Comment> Comments)
{

}