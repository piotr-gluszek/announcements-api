package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrgluszek.announcements.dto.AnnouncementDto;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.services.AnnouncementsService;

import java.net.URI;


@RestController
@RequestMapping("announcements")
public class AnnouncementsController {
    @Autowired
    AnnouncementsService announcementsService;

    @GetMapping("/{id}")
    ResponseEntity<AnnouncementEntity> findById(@PathVariable("id") long id){
        //TODO: increment view counter
        return ResponseEntity.ok(announcementsService.findAnnouncementById(id).orElse(null));
    }

    //TODO: return uri after creation
    @PostMapping
    ResponseEntity create(AnnouncementDto announcementDto) throws Exception{
        return ResponseEntity.created(new URI("")).build();
    }


}
