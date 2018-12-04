package pl.piotrgluszek.announcements.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface  AnnouncementEntityMapper {
    void updateAnnouncement(AnnouncementEntity announcementEntity, @MappingTarget AnnouncementEntity target);
}
