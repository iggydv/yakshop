package com.xebia.yakshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.xebia.yakshop.controllers.YakShopController;
import com.xebia.yakshop.models.HerdStatus;
import com.xebia.yakshop.models.LabYakRs;
import com.xebia.yakshop.models.Order;
import com.xebia.yakshop.models.Stock;
import com.xebia.yakshop.storage.HerdStorageImpl;
import com.xebia.yakshop.storage.OrderHistoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {YakShopController.class, OrderService.class, HerdStorageImpl.class, OrderHistoryImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        String json = ow.writeValueAsString(Stock.builder().milk(1104.48).skins(3).build());

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

    @Test
    void order() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(Order.builder().customer("Yak Man").stock(Stock.builder().milk(1100.00).skins(3).build()).build());
        String jsonResponse = ow.writeValueAsString(Stock.builder().milk(1100.00).skins(3).build());

        mockMvc.perform(post("/yak-shop/order/{T}", 14)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse))
                .andReturn();
    }

    @Test
    void orderWithPartialResponse() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(Order.builder().customer("Yak Man").stock(Stock.builder().milk(1200.00).skins(3).build()).build());
        String jsonResponse = ow.writeValueAsString(Stock.builder().skins(3).build());

        mockMvc.perform(post("/yak-shop/order/{T}", 14)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPartialContent())
                .andExpect(content().json(jsonResponse))
                .andReturn();
    }

    @Test
    void orderWithPartialRequest() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(Order.builder().customer("Yak Man").stock(Stock.builder().skins(3).build()).build());
        String jsonResponse = ow.writeValueAsString(Stock.builder().skins(3).build());

        mockMvc.perform(post("/yak-shop/order/{T}", 14)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse))
                .andReturn();
    }

    @Test
    void orderWithAllItemsUndeliverable() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(Order.builder().customer("Yak Man").stock(Stock.builder().milk(1200.00).skins(10).build()).build());

        mockMvc.perform(post("/yak-shop/order/{T}", 14)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void emptyOrder() throws Exception {
        loadHerd();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(Order.builder().customer("Yak Man").stock(Stock.builder().milk(0.0).skins(0).build()).build());

        mockMvc.perform(post("/yak-shop/order/{T}", 14)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}