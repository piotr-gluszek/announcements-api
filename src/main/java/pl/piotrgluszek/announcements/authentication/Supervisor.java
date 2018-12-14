package pl.piotrgluszek.announcements.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotrgluszek.announcements.services.AnnouncementsService;

import java.util.NoSuchElementException;

@Component
public class Supervisor {
    @Autowired
    AnnouncementsService announcementsService;

    public boolean isOwner(Long announcementId, Long userId) {
        try {
            return announcementsService.findById(announcementId).getAnnouncer().getId() == userId;
        } catch (NoSuchElementException nse) {
            // if resource DOES NOT exist let announcement controller fail
            return true;
        }
    }
}
