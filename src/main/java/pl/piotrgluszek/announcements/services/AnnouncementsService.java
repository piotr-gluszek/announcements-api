package pl.piotrgluszek.announcements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.repositories.AnnouncementsRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnnouncementsService {
    public static final String NO_SUCH_ANNOUNCEMENT = "Announcement with id [%d] does not exist";

    @Autowired
    AnnouncementsRepository announcementsRepository;

    public Optional<AnnouncementEntity> findAnnouncementById(long id) {
        //TODO: will this operation always mean incrementing views counter?
        return announcementsRepository.findById(id);
    }

    public AnnouncementEntity create(AnnouncementEntity announcementEntity) {
        return announcementsRepository.save(announcementEntity);
    }

    public AnnouncementEntity update(AnnouncementEntity announcementEntity) {
        if(announcementsRepository.existsById(announcementEntity.getId()))
            return announcementsRepository.save(announcementEntity);
        else throw new NoSuchElementException(String.format(NO_SUCH_ANNOUNCEMENT, announcementEntity.getId()));
    }
}
