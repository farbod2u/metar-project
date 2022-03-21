package com.t2e.metarproject.service;

import com.t2e.metarproject.entity.Metar;
import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.InvalidMetarException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.repository.MetarRepository;
import com.t2e.metarproject.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MetarServiceTest {

    @InjectMocks
    private MetarService underTest;

    @Mock
    private MetarRepository metarRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    void getLastMetarByIcaoCode_found() {
        //given
        String icaoCode = "OIII";
        var subscription = new Subscription(icaoCode, null);
        var metar = new Metar(null, "METAR " + icaoCode + " 121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), subscription);

        given(metarRepository.getLastMetarByIcaoCode(icaoCode)).willReturn(Optional.of(metar));

        //when
        underTest.getLastMetarByIcaoCode(icaoCode);

        //then
        verify(metarRepository).getLastMetarByIcaoCode(icaoCode);
    }

    @Test
    void getLastMetarByIcaoCode_not_found() {
        //given
        given(metarRepository.getLastMetarByIcaoCode(anyString())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> underTest.getLastMetarByIcaoCode(anyString()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Metar Data not fount for ");
    }

    @Test
    void save() {
        //given
        String icaoCode = "OIII";
        var subscription = new Subscription(icaoCode, null);
        var metar = new Metar(null, "METAR " + icaoCode + " 121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), subscription);

        given(metarRepository.save(metar)).willReturn(metar);
        given(subscriptionRepository.getByIcaoCode(icaoCode)).willReturn(Optional.of(subscription));

        //when
        Metar result = underTest.save(icaoCode, metar);

        //then
        verify(metarRepository).save(metar);
        assertThat(metar).isEqualTo(result);

    }

    @Test
    void save_icaoCode_notfound() {
        //given
        String icaoCode = "OIII";
        var metar = new Metar(null, "METAR " + icaoCode + " 121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), null);
        given(subscriptionRepository.getByIcaoCode(anyString())).willReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> underTest.save(anyString(), metar))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("ICAO Code not found");
    }

    @Test
    void save_invalid_metar() {
        //given
        String icaoCode = "OIII";
        var metar = new Metar(null, "121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), null);

        //when

        //then
        assertThatThrownBy(() -> underTest.save(icaoCode, metar))
                .isInstanceOf(InvalidMetarException.class)
                .hasMessageContaining("Invalid METAR data");
    }

    @Test
    void save_with_error() {
        //given
        String icaoCode = "OIII";
        var subscription = new Subscription(icaoCode, null);
        var metar = new Metar(null, "METAR " + icaoCode + " 121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), subscription);

        given(metarRepository.save(metar)).willThrow(RequestException.class);
        given(subscriptionRepository.getByIcaoCode(icaoCode)).willReturn(Optional.of(subscription));

        //when

        //then
        assertThatThrownBy(() -> underTest.save(icaoCode, metar))
                .isInstanceOf(RequestException.class);

    }

}