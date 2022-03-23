package com.t2e.metarproject.service;

import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService underTest;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void save() {
        //given
        String icaoCode = "LZDA";
        Subscription entity = new Subscription(icaoCode, null, 1);
        given(subscriptionRepository.save(entity)).willReturn(entity);

        //when
        Subscription result = underTest.save(icaoCode);

        //then
        verify(subscriptionRepository).save(entity);
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void save_bad_icaoCode() {
        //given
        String icaoCode = "IZDA";

        //when

        //then
        assertThatThrownBy(() -> underTest.save(icaoCode))
                .isInstanceOf(RequestException.class)
                .hasMessageContaining("ICAO code is incorrect.");
    }

    @Test
    void save_with_error() {
        //given
        String icaoCode = "LZDA";
        Subscription entity = new Subscription(icaoCode, null, 1);
        String error = "Error";
        given(subscriptionRepository.save(entity)).willThrow(new RequestException(error));

        //when

        //then

        assertThatThrownBy(() -> underTest.save(icaoCode))
                .isInstanceOf(RequestException.class)
                .hasMessageContaining(error);
    }

//    @Test
//    void getAll() {
//        //given
//        given(subscriptionRepository.findAll()).willReturn(List.of());
//
//        //when
//        underTest.getAll();
//
//        //then
//        verify(subscriptionRepository).findAll();
//    }

    @Test
    void delete_without_error() {
        //given
        String icaoCode = "LDZA";
        Subscription entity = new Subscription(icaoCode, null, 1);
        given(subscriptionRepository.getByIcaoCode(icaoCode)).willReturn(Optional.of(entity));

        //when
        Subscription result = underTest.delete(icaoCode);

        //then
        verify(subscriptionRepository).delete(entity);
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void delete_with_error() {
        //given
        given(subscriptionRepository.getByIcaoCode(anyString())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> underTest.delete(anyString()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("ICAO Code not found");
    }

    @Test
    void enable() {
        //given
        String icaoCode = "OIII";
        int active = 0;
        Subscription entity = new Subscription(icaoCode, null, active);
        given(subscriptionRepository.getByIcaoCode(icaoCode)).willReturn(Optional.of(entity));

        //when
        Subscription result = underTest.enable(icaoCode, active);

        //then
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void enable_invalid_active() {
        //given
        String icaoCode = "OIII";
        int active = 10;

        //when
        //then
        assertThatThrownBy(() -> underTest.enable(icaoCode, active))
                .isInstanceOf(RequestException.class)
                .hasMessageContaining("Valid values for Active are 0 and 1.");
    }

    @Test
    void enable_invalid_icaoCode() {
        //given
        int active = 0;
        given(subscriptionRepository.getByIcaoCode(anyString())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.enable(anyString(), active))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("ICAO Code not found");
    }
}