package fun.cut4me;

import fun.cut4me.repository.LongLinkRepository;
import fun.cut4me.repository.ShortLinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static fun.cut4me.config.SpringConfigForIT.TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(TEST_PROFILE)
@AutoConfigureMockMvc
class LinkShortenerApplicationTests {

    private static final String TEST_LINK = "https://vk.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LongLinkRepository longLinkRepository;

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @BeforeEach
    public void clearRepo() {
        longLinkRepository.deleteAll();
        shortLinkRepository.deleteAll();
    }

    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("сокращатель ссылок 2.0")));
    }

    @Test
    public void addLinkTest() throws Exception {
        mockMvc.perform(post("/")
                        .param("link", TEST_LINK)
                        .content(TEST_LINK))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(longLinkRepository.findAll()
                .get(0).getUserLink()).isEqualTo(TEST_LINK);
    }

    @Test
    public void generateShortLinkTest() throws Exception {
        mockMvc.perform(post("/")
                        .param("link", TEST_LINK)
                        .content(TEST_LINK))
                .andExpect(status().isOk())
                .andReturn();

        Long longLinkId = longLinkRepository.findAll().get(0).getId();
        Long shortLinkId = shortLinkRepository.findAll().get(0).getId();

        assertThat(longLinkId).isEqualTo(shortLinkId);
    }

    @Test
    public void getLongLinkTest() throws Exception {
        mockMvc.perform(post("/")
                        .param("link", TEST_LINK)
                        .content(TEST_LINK))
                .andExpect(status().isOk())
                .andReturn();

        String shortUrl = shortLinkRepository.findAll().get(0).getGeneratedLink();

        mockMvc.perform(get("/" + shortUrl))
                .andExpect(redirectedUrl(TEST_LINK))
                .andReturn();
    }
}
