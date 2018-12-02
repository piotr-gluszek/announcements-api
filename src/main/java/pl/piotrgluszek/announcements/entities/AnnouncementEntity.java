package pl.piotrgluszek.announcements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
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
    @ManyToMany
    @JsonIgnoreProperties("announcements")
    @JoinTable(name = "announcementscategories",
            joinColumns = @JoinColumn(name = "announcementid"),
            inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    List<CategoryEntity> categories;
}
