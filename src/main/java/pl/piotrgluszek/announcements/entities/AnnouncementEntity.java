package pl.piotrgluszek.announcements.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Announcements")
@Entity
public class AnnouncementEntity {

    @Id
    @GeneratedValue
    long id;
    @JsonIgnoreProperties("announcements")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcerid")
    UserEntity announcer;
    Timestamp date;
    long views;
    String description;
    String title;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnoreProperties("announcements")
    @JoinTable(name = "announcementscategories",
            joinColumns = @JoinColumn(name = "announcementid"),
            inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    List<CategoryEntity> categories;

    public AnnouncementEntity(){
        categories = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public long getAnnouncerId() {
//        return announcerId;
//    }
//
//    public void setAnnouncerId(long announcerId) {
//        this.announcerId = announcerId;
//    }

    public UserEntity getAnnouncer() {
        return announcer;
    }

    public void setAnnouncer(UserEntity announcer) {
        this.announcer = announcer;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }
}
