package pl.piotrgluszek.announcements.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.piotrgluszek.announcements.configuration.AppConfig;
import pl.piotrgluszek.announcements.controllers.AnnouncementsController;
import pl.piotrgluszek.announcements.entities.AnnouncementEntity;
import pl.piotrgluszek.announcements.services.AnnouncementsService;
import pl.piotrgluszek.announcements.services.CategoriesService;
import pl.piotrgluszek.announcements.services.UserService;

import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnnouncementsController.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AnnouncementControllerTests {
    MockMvc mockMvc;
    @MockBean
    AnnouncementsService announcementsService;
    @MockBean
    UserService userService;
    @MockBean
    CategoriesService categoriesService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void givenExistingIdReturnUserEntity() throws Exception {
        AnnouncementEntity sampleAnnouncement = new AnnouncementEntity().setId(1L)
                .setTitle("Sample title")
                .setDescription("Sample description")
                .setDate(Timestamp.from(Instant.now()))
                .setViews(0L);

        when(announcementsService.findById(1L)).thenReturn(sampleAnnouncement);
        when(announcementsService.update(any())).thenReturn(sampleAnnouncement);
        this.mockMvc.perform(get("/announcements/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(sampleAnnouncement)));
    }

}
