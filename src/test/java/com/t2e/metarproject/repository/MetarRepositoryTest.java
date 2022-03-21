package com.t2e.metarproject.repository;

import com.t2e.metarproject.entity.Metar;
import com.t2e.metarproject.entity.Subscription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MetarRepositoryTest {

    @Autowired
    private MetarRepository metarRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @AfterEach
    void afterEach() {
        metarRepository.deleteAll();
        subscriptionRepository.deleteAll();
    }

    @Test
    void getLastMetarByIcaoCode() {
        //given
        String icaoCode = "OIII";
        var subscription = subscriptionRepository.save(new Subscription(icaoCode, null));
        var metar = new Metar(null, "METAR " + icaoCode + " 121200Z 0902MPS 090V150 2000 " +
                "R04/P2000N R22/P2000N OVC050 0/M01 Q1020=", LocalDateTime.now(), subscription);
        metarRepository.save(metar);

        //when
        Optional<Metar> result = metarRepository.getLastMetarByIcaoCode(icaoCode);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(metar);
    }
}