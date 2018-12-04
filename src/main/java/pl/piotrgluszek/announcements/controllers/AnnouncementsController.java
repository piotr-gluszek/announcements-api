package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.error.ErrorEntity;
import pl.piotrgluszek.announcements.services.AnnouncementsService;

import java.net.URI;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;

    @GetMapping("/{id}")
    ResponseEntity<AnnouncementEntity> findById(@PathVariable("id") long id) {
        //TODO: increment view counter
        return ResponseEntity.ok(announcementsService.findAnnouncementById(id).orElse(null));
    }

    @PostMapping
    ResponseEntity create(@RequestBody AnnouncementEntity announcementEntity) throws Exception {
        AnnouncementEntity createdAnnouncement = announcementsService.create(announcementEntity);
        return ResponseEntity.created(new URI("/annoumcements/" + createdAnnouncement.getId())).body(createdAnnouncement);
    }

    @PutMapping("/{id}")
    ResponseEntity update(@PathVariable("id") long id,
                          @RequestBody AnnouncementEntity announcementEntity) {
        try {
            return ResponseEntity.ok(announcementsService.update(announcementEntity.setId(id)));
        } catch (NoSuchElementException nse) {
            return ResponseEntity.badRequest().body(new ErrorEntity().setMessage(nse.getMessage()));
        }
    }


}
