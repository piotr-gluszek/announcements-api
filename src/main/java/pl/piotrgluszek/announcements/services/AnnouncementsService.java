package pl.piotrgluszek.announcements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrgluszek.announcements.dto.AnnouncementDto;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.repositories.AnnouncementsRepository;

import java.util.Optional;

@Service
public class AnnouncementsService {

    @Autowired
    AnnouncementsRepository announcementsRepository;

    public Optional<AnnouncementEntity> findAnnouncementById(long id){
        return announcementsRepository.findById(id);
    }
    public Long create(AnnouncementDto announcementDto){
        //TODO: use mapper here
        AnnouncementEntity announcementEntity = new AnnouncementEntity();
        return  0L; //announcementsRepository.save(announcementEntity).getId();
    }
}
