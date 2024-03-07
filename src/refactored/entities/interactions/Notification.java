package refactored.entities.interactions;

import java.time.LocalDateTime;

/**
 * Basically only here to remind that every interaction can be a notification
 *  // when the time comes define the appropriate methods
 */
public interface Notification {
    public int getID();
    public LocalDateTime getTimestamp();
    public int getSenderID();
    public int getReceiverID();
    public String getNotificationMessage();
}
