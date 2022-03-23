package com.t2e.metarproject.repository;

import com.t2e.metarproject.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String>, JpaSpecificationExecutor<Subscription> {
    @Query("select s from Subscription s where s.icaoCode = ?1 ")
    Optional<Subscription> getByIcaoCode(String icaoCode);
}
