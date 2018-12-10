package pl.piotrgluszek.announcements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrgluszek.announcements.entities.CategoryEntity;
import pl.piotrgluszek.announcements.repositories.CategoriesRepository;

import java.util.NoSuchElementException;

@Service
public class CategoriesService {
    public static final String NO_SUCH_CATEGORY = "Category with id [%d] does not exist";
    @Autowired
    CategoriesRepository categoriesRepository;

    public CategoryEntity findById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new NoSuchElementException(NO_SUCH_CATEGORY));
    }
}
