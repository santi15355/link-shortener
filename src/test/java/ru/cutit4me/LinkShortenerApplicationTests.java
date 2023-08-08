package ru.cutit4me;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.cutit4me.config.SpringConfigForIT;
import ru.cutit4me.model.Url;
import ru.cutit4me.repository.UrlRepository;
import ru.cutit4me.service.UrlService;
import ru.cutit4me.utils.UrlEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles(SpringConfigForIT.TEST_PROFILE)
@AutoConfigureMockMvc
class LinkShortenerApplicationTests {

    private static final String LONG_LINK = "https://vk.com";
    private static final String LONG_LINK_2 = "https://vk.com/?utm_source=yandex&utm_medium";
    private static final String SHORT_LINK = "Gt6hI";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlService urlService;

    @BeforeEach
    public void clearRepo() {
        urlRepository.deleteAll();
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
                        .param("link", LONG_LINK)
                        .content(LONG_LINK))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(urlRepository.findAll()
                .get(0).getLongUrl()).isEqualTo(LONG_LINK);
    }

    @Test
    @Transactional
    public void getLongLinkTest() throws Exception {
        Url url = new Url();
        url.setLongUrl(LONG_LINK);
        url.setShortUrl(SHORT_LINK);
        urlService.saveUrl(url);

        mockMvc.perform(get("/")
                        .param("link", SHORT_LINK)
                        .content(SHORT_LINK))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(urlService.findLongLink(SHORT_LINK)).isEqualTo(LONG_LINK);


    }

    @Test
    public void showAllUrlTest() throws Exception {
        Url url = new Url();
        url.setLongUrl(LONG_LINK);
        url.setShortUrl("Gh2gI");
        urlRepository.save(url);

        mockMvc.perform(get("/show"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("https://vk.com")))
                .andExpect(content()
                        .string(containsString("Всего ссылок")));

    }

    @Test
    public void urlEncodingTest() {
        var actual = UrlEncoder.encodeUrl(LONG_LINK_2);
        assertThat(LONG_LINK_2).isEqualTo(actual);
    }
}
