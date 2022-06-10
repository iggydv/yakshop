package com.xebia.yakshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("h2")
@ContextConfiguration(classes = {YakShopController.class})
@WebMvcTest(YakShopController.class)
class YakShopControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "\n<herd>\n" +
            "<labyak name=\"Betty-1\" age=\"4\" sex=\"f\"/>\n" +
            "<labyak name=\"Betty-2\" age=\"8\" sex=\"f\"/>\n" +
            "<labyak name=\"Betty-3\" age=\"9.5\" sex=\"f\"/>\n" +
            "</herd>";

    @Test
    void loadHerd() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/yak-shop/load")
                        .content(xmlContent)
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isResetContent())
                .andReturn();
    }
}