package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.entities.CategoryEntity;
import pl.piotrgluszek.announcements.model.ApiMessage;
import pl.piotrgluszek.announcements.services.CategoriesService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("categories")
public class CategoriesController {
    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(categoriesService.findById(id));
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage().setMessage(nse.getMessage()));
        }
    }

    @GetMapping("/{id}/announcements")
    public ResponseEntity findAnnouncementsByCategory(@PathVariable("id") Long id, Pageable pageable) {
        try {
            return ResponseEntity.ok(paginate(categoriesService.findById(id).getAnnouncements(), pageable));
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage().setMessage(nse.getMessage()));
        }
    }

    Page<AnnouncementEntity> paginate(List list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
        return new PageImpl<AnnouncementEntity>(list.subList(start, end), pageable, list.size());

    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> getAll(){
        return ResponseEntity.ok(categoriesService.getAll());
    }


}
