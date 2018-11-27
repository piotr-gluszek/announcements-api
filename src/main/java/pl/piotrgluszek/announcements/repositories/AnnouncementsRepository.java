package pl.piotrgluszek.announcements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;

@Repository
public interface AnnouncementsRepository extends JpaRepository<AnnouncementEntity, Long> {
}
