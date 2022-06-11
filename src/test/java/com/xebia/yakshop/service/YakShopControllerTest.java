package com.xebia.yakshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.xebia.yakshop.models.HerdInternal;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakInternal;
import com.xebia.yakshop.models.LabYakRs;
import com.xebia.yakshop.models.SexInternal;
import com.xebia.yakshop.models.StockRs;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import spock.lang.Subject;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("h2")
@ContextConfiguration(classes = {YakShopController.class, HerdInternal.class})
@WebMvcTest(YakShopController.class)
class YakShopControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HerdInternal herdInternal;

    private String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "\n<herd>\n" +
            "<labyak name=\"Betty-1\" age=\"4\" sex=\"f\"/>\n" +
            "<labyak name=\"Betty-2\" age=\"8\" sex=\"f\"/>\n" +
            "<labyak name=\"Betty-3\" age=\"9.5\" sex=\"f\"/>\n" +
            "</herd>";

    @Test
    void loadHerd() throws Exception {
        mockMvc.perform(post("/yak-shop/load")
                        .content(xmlContent)
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isResetContent())
                .andReturn();
    }

    @Test
    void getStock() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(StockRs.builder().milk(1104.48).skins(3).build());

        mockMvc.perform(get("/yak-shop/stock/{T}", 13))
                .andExpect(status().isOk())
                .andExpect(content().json(json))
                .andReturn();
    }

    @Test
    void getHerdStatus() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final List<LabYakRs> herdResult = List.of(
                LabYakRs.builder().age(4.13).name("Betty-1").ageLastShaved(4.0).build(),
                LabYakRs.builder().age(8.13).name("Betty-2").ageLastShaved(8.0).build(),
                LabYakRs.builder().age(9.63).name("Betty-3").ageLastShaved(9.5).build());

        String json = ow.writeValueAsString(HerdStatus.builder().herd(herdResult).build());

        mockMvc.perform(get("/yak-shop/herd/{T}", 13))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json))
                .andReturn();
    }
}