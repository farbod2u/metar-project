package com.t2e.metarproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2e.metarproject.entity.Metar;
import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.InvalidMetarException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.service.MetarService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MetarService metarService;

//    @SneakyThrows
//    @Test
//    void getLastMetarByIcaoCode() {
//        //given
//        var icaoCode = "OIII";
//        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), null, null,null,null,null);
//        given(metarService.getLastMetarByIcaoCode(icaoCode)).willReturn(metar);
//
//        var url = "/airport/" + icaoCode + "/METAR";
//
//        //when
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        var actualResult = mvcResult.getResponse().getContentAsString();
//        var excpectedresult = objectMapper.writeValueAsString(metar);
//
//        //then
//        assertThat(actualResult).isEqualTo(excpectedresult);
//    }

    @SneakyThrows
    @Test
    void getLastMetarByIcaoCode_notfound() {
        //given
        var icaoCode = "OIII";
        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), null, null,null,null,null);
        given(metarService.getLastMetarByIcaoCode(icaoCode)).willThrow(EntityNotFoundException.class);

        var url = "/airport/" + icaoCode + "/METAR";

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isNotFound())
                .andReturn();
    }

//    @Test
//    @SneakyThrows
//    void save() {
//        //given
//        var icaoCode = "OIII";
//        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), new Subscription(icaoCode, null, 1), null,null,null,null);
//        given(metarService.save(icaoCode, metar)).willReturn(metar);
//
//        var url = "/airport/" + icaoCode + "/METAR";
//
//        //when
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(metar)))
//                .andExpect(status().isOk())
//                .andReturn();
//        var actualResult = mvcResult.getResponse().getContentAsString();
//        var excpectedResult = objectMapper.writeValueAsString(metar);
//
//        //then
//        assertThat(actualResult).isEqualTo(excpectedResult);
//
//    }
//
//    @Test
//    @SneakyThrows
//    void save_icaoCode_notfound() {
//        //given
//        var icaoCode = "OIII";
//        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), new Subscription(icaoCode, null, 1), null,null,null,null);
//        given(metarService.save(icaoCode, metar)).willThrow(EntityNotFoundException.class);
//
//        var url = "/airport/" + icaoCode + "/METAR";
//
//        //when
//        //then
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(metar)))
//                .andExpect(status().isNotFound())
//                .andReturn();
//
//    }
//
//    @Test
//    @SneakyThrows
//    void save_invalidMetar() {
//        //given
//        var icaoCode = "OIII";
//        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), new Subscription(icaoCode, null, 1), null,null,null,null);
//        given(metarService.save(icaoCode, metar)).willThrow(InvalidMetarException.class);
//
//        var url = "/airport/" + icaoCode + "/METAR";
//
//        //when
//        //then
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(metar)))
//                .andExpect(status().isNotAcceptable())
//                .andReturn();
//
//    }
//
//    @Test
//    @SneakyThrows
//    void save_withError() {
//        //given
//        var icaoCode = "OIII";
//        var metar = new Metar(null, "METAR_DATA", LocalDateTime.now(), new Subscription(icaoCode, null, 1), null,null,null,null);
//        given(metarService.save(icaoCode, metar)).willThrow(RequestException.class);
//
//        var url = "/airport/" + icaoCode + "/METAR";
//
//        //when
//        //then
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(metar)))
//                .andExpect(status().isNotAcceptable())
//                .andReturn();
//
//    }
}