package pl.piotrgluszek.announcements.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.entities.CategoryEntity;

@Repository
public interface AnnouncementsRepository extends JpaRepository<AnnouncementEntity, Long> {
    @Query("SELECT ann FROM AnnouncementEntity ann WHERE ?1 IS NULL OR ?1 MEMBER OF ann.categories")
    Page<AnnouncementEntity> findByCategoriesContains(CategoryEntity category, Pageable pageable);
}
