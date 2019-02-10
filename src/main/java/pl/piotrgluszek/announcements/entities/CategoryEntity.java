package pl.piotrgluszek.announcements.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"announcements"})
@Table(name = "Categories")
@Entity
public class CategoryEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    List<AnnouncementEntity> announcements;
}
