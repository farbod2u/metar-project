package com.t2e.metarproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.service.SubscriptionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubscriptionService subscriptionService;

    @SneakyThrows
    @Test
    void getAll() {
        //given
        var subscriptions = List.of(
                new Subscription("COD1", null, 1),
                new Subscription("COD2", null, 1)
        );
        given(subscriptionService.getAll()).willReturn(subscriptions);

        String url = "/subscriptions";

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(subscriptions);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @SneakyThrows
    @Test
    void save() {
        //given
        String icaoCode = "COD1";
        var entity = new Subscription(icaoCode, null, 1);
        given(subscriptionService.save(icaoCode)).willReturn(entity);

        var url = "/subscriptions";

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity))
                ).andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(entity);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);

    }

    @SneakyThrows
    @Test
    void save_invalid_icaoCode() {
        //given
        String icaoCode = "COD1";
        var entity = new Subscription(icaoCode, null, 1);
        given(subscriptionService.save(anyString())).willThrow(RequestException.class);

        var url = "/subscriptions";

        //when
        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity))
                ).andExpect(status().isNotAcceptable())
                .andReturn();
    }

    @SneakyThrows
    @Test
    void delete() {
        //given
        String icaoCado = "OIII";
        var subscription = new Subscription(icaoCado, null, 1);
        given(subscriptionService.delete(icaoCado)).willReturn(subscription);

        var url = "/subscriptions/" + icaoCado;

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, url))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(subscription);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @SneakyThrows
    @Test
    void delete_with_icaoCode_not_found() {
        //given
        given(subscriptionService.delete(anyString())).willThrow(EntityNotFoundException.class);

        var url = "/subscriptions/OIII";

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.DELETE, url))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @SneakyThrows
    @Test
    void active() {
        //given
        String icaoCado = "OIII";
        int active = 1;
        var subscription = new Subscription(icaoCado, null, active);
        given(subscriptionService.enable(icaoCado, active)).willReturn(subscription);

        var url = "/subscriptions/" + icaoCado;

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscription)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(subscription);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}