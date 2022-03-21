package com.t2e.metarproject.repository;

import com.t2e.metarproject.entity.Metar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetarRepository extends JpaRepository<Metar, Long> {

//    @Query("select m from Metar m where m.subscription.icaoCode = ?1")
//    List<Metar> getMetarByIcaoCode(String icaoCode);

    @Query("select m from Metar m " +
            "where m.subscription.icaoCode = ?1 " +
            "and m.saveDate = (select max(b.saveDate) from Metar b where b.subscription.icaoCode = ?1)")
    Optional<Metar> getLastMetarByIcaoCode(String icaoCode);
}
