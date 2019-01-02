package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.entities.CategoryEntity;
import pl.piotrgluszek.announcements.entities.UserEntity;
import pl.piotrgluszek.announcements.model.ApiMessage;
import pl.piotrgluszek.announcements.services.AnnouncementsService;
import pl.piotrgluszek.announcements.services.CategoriesService;
import pl.piotrgluszek.announcements.services.UserService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;
    @Autowired
    UserService userService;
    @Autowired
    CategoriesService categoriesService;


    @GetMapping
    public ResponseEntity<Page<AnnouncementEntity>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(announcementsService.findAll(pageable));
    }

    @GetMapping("/from-category/{id}")
    public ResponseEntity<Page<AnnouncementEntity>> findAllByCategory(@PathVariable("id") Long id,Pageable pageable) {
        CategoryEntity category = categoriesService.findById(id);
        return ResponseEntity.ok().body(announcementsService.findAllCategoriesContains(category, pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") long id) {
        try {
            AnnouncementEntity announcement = announcementsService.findById(id);
            Long views = announcement.getViews();
            announcementsService.update(announcement.setViews(views + 1));
            return ResponseEntity.ok(announcement.setViews(views));

        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(nse.getMessage()));
        }
    }

    @PutMapping("/{id}/increment-views")
    public ResponseEntity incrementViews(@PathVariable("id") long id) {
        try {
            AnnouncementEntity announcement = announcementsService.findById(id);
            Long views = announcement.getViews();
            AnnouncementEntity updatesToAnnouncement = new AnnouncementEntity().setViews(views+1).setId(id);
            announcementsService.update(updatesToAnnouncement);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(nse.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody AnnouncementEntity announcementEntity) throws Exception {
        Long authenticatedUsedId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity authenticatedUser = userService.findById(authenticatedUsedId);
        announcementEntity.setDate(Timestamp.from(Instant.now()))
                          .setAnnouncer(authenticatedUser)
                          .setViews(0L);
        AnnouncementEntity createdAnnouncement = announcementsService.create(announcementEntity);
        return ResponseEntity.created(new URI("/announcements/" + createdAnnouncement.getId())).body(createdAnnouncement);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@supervisor.isOwner(#id, principal)")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody AnnouncementEntity announcementEntity) {
        try {
            return ResponseEntity.ok(announcementsService.update(announcementEntity.setId(id)));
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(nse.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@supervisor.isOwner(#id, principal)")
    public ResponseEntity delete(@PathVariable("id") long id) {
        try {
            announcementsService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage(nse.getMessage()));
        }
    }
}
