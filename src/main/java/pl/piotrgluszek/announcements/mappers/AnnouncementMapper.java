package pl.piotrgluszek.announcements.mappers;

import org.mapstruct.Mapper;
import pl.piotrgluszek.announcements.dto.AnnouncementDto;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;

@Mapper
public interface AnnouncementMapper {
    AnnouncementDto toAnnouncementDto(AnnouncementEntity announcementEntity);
    AnnouncementEntity fromAnnouncementDto(AnnouncementDto announcementDto);
}

