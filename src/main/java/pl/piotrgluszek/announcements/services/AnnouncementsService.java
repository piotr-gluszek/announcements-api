package pl.piotrgluszek.announcements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.mappers.AnnouncementEntityMapper;
import pl.piotrgluszek.announcements.repositories.AnnouncementsRepository;

import java.util.NoSuchElementException;

@Service
public class AnnouncementsService {
    public static final String NO_SUCH_ANNOUNCEMENT = "Announcement with id [%d] does not exist";

    @Autowired
    AnnouncementsRepository announcementsRepository;
    @Autowired
    AnnouncementEntityMapper announcementEntityMapper;

    public AnnouncementEntity findById(long id) {
        //TODO: will this operation always mean incrementing views counter?
        return announcementsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_SUCH_ANNOUNCEMENT, id)));
    }

    public AnnouncementEntity create(AnnouncementEntity announcementEntity) {
        return announcementsRepository.save(announcementEntity);
    }

    public AnnouncementEntity update(AnnouncementEntity announcementEntity) {
        if (announcementsRepository.existsById(announcementEntity.getId())) {
            AnnouncementEntity originalEntity = announcementsRepository.findById(announcementEntity.getId()).get();
            announcementEntityMapper.updateAnnouncement(announcementEntity, originalEntity);
            return announcementsRepository.save(originalEntity);
        } else throw new NoSuchElementException(String.format(NO_SUCH_ANNOUNCEMENT, announcementEntity.getId()));
    }

    public void delete(Long id) {

        try {
            announcementsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ersae) {
            throw new NoSuchElementException(String.format(NO_SUCH_ANNOUNCEMENT, id));
        }
    }
}
