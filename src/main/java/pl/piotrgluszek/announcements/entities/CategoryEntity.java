package pl.piotrgluszek.announcements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"announcements"})
@Table(name = "Categories")
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    List<AnnouncementEntity> announcements;


    public CategoryEntity() {
        announcements = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnnouncementEntity> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementEntity> announcements) {
        this.announcements = announcements;
    }
}
