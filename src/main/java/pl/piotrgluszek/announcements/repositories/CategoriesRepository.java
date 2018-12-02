package pl.piotrgluszek.announcements.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrgluszek.announcements.entities.CategoryEntity;

@Repository
public interface CategoriesRepository extends JpaRepository<CategoryEntity, Long> {
}
