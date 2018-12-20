package pl.piotrgluszek.announcements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "Announcements")
@Entity
public class AnnouncementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonIgnoreProperties("announcements")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcerid")
    UserEntity announcer;
    Timestamp date;
    Long views;
    String description;
    String title;
    @ManyToMany
    @JsonIgnoreProperties("announcements")
    @JoinTable(name = "announcementscategories",
            joinColumns = @JoinColumn(name = "announcementid"),
            inverseJoinColumns = @JoinColumn(name = "categoryid")
    )
    List<CategoryEntity> categories;
    String photo;
}
