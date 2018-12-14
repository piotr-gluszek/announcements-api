package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.model.ApiMessage;
import pl.piotrgluszek.announcements.services.AnnouncementsService;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;


    @GetMapping
    public ResponseEntity<Page<AnnouncementEntity>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(announcementsService.findAll(pageable));
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

    @PostMapping
    public ResponseEntity create(@RequestBody AnnouncementEntity announcementEntity) throws Exception {
        AnnouncementEntity createdAnnouncement = announcementsService.create(announcementEntity.setDate(Timestamp.from(Instant.now())));
        return ResponseEntity.created(new URI("/annoumcements/" + createdAnnouncement.getId())).body(createdAnnouncement);
    }

    @PutMapping("/{id}")
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
