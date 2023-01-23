package com.shortit4me;

import com.shortit4me.repository.LongLinkRepository;
import com.shortit4me.repository.ShortLinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.shortit4me.config.SpringConfigForIT.TEST_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .andExpect(content()
                        .string(containsString("shortit4me")))
                .andReturn();
        assertThat(longLinkRepository.findAll()
                .get(0).getUserLink()).isEqualTo(TEST_LINK);
    }
}
