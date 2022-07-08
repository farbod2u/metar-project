package com.t2e.metarproject.repository;

import com.t2e.metarproject.entity.Subscription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @AfterEach
    void afterEach(){
        subscriptionRepository.deleteAll();
    }

    @Test
    void getByIcaoCode_found() {
        //given
        String icaoCode = "LZDA";
        Subscription entity = new Subscription(icaoCode, null, 1);
        subscriptionRepository.save(entity);

        //when
        Optional<Subscription> result = subscriptionRepository.getByIcaoCode(icaoCode);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getIcaoCode()).isEqualTo(icaoCode);
    }

    @Test
    void getByIcaoCode_notfound() {
        //given
        String icaoCode = "LZDA";

        //when
        Optional<Subscription> result = subscriptionRepository.getByIcaoCode(icaoCode);

        //then
        assertThat(result.isPresent()).isFalse();
    }
}