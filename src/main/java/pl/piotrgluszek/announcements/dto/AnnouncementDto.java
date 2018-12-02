package pl.piotrgluszek.announcements.dto;

import lombok.Getter;
import lombok.Setter;
import pl.piotrgluszek.announcements.entities.CategoryEntity;
import pl.piotrgluszek.announcements.entities.UserEntity;

import java.util.List;

@Getter
@Setter
public class AnnouncementDto {
    long id;
    UserEntity announcer;
    String date;
    String title;
    String description;
    List<CategoryEntity> categories;
    long timestampMillis;
}
