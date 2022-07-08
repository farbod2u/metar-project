package com.t2e.metarproject.service;

import com.t2e.metarproject.entity.Metar;
import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.InvalidMetarException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.parser.MetarParseException;
import com.t2e.metarproject.parser.MetarParser;
import com.t2e.metarproject.repository.MetarRepository;
import com.t2e.metarproject.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MetarService {

    private final MetarRepository metarRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Metar getLastMetarByIcaoCode(String icaoCode) {
        icaoCode = icaoCode.toUpperCase();
        var result = metarRepository.getLastMetarByIcaoCode(icaoCode);
        if (!result.isPresent())
            throw new EntityNotFoundException("Metar Data not fount for " + icaoCode);
        return result.get();
    }

    private void checkMetardata(String icaoCode, String metar) {
        if (!metar.matches("METAR " + icaoCode + "[[\\s][0-9A-Z\\+\\-\\/]]+="))
            throw new InvalidMetarException();
    }

    public Metar save(String icaoCode, Metar entity) {
        icaoCode = icaoCode.toUpperCase();
        entity.setData(entity.getData().toUpperCase());
        checkMetardata(icaoCode, entity.getData());

        Optional<Subscription> subscription = subscriptionRepository.getByIcaoCode(icaoCode);
        if (!subscription.isPresent())
            throw new EntityNotFoundException("ICAO Code not found");


        try {
            com.t2e.metarproject.parser.Metar metar = MetarParser.parse(entity.getData().replace("METAR ", "").replace("=", ""));
            entity.setTemperature(metar.getTemperatureInCelsius());
            entity.setTimestamp(LocalDateTime.of(metar.getDate().getYear(), metar.getDate().getMonth(), metar.getDate().getDay(), metar.getDate().getHours(), metar.getDate().getMinutes()));
            entity.setVisibility(metar.getVisibility());
            entity.setWindSpeed(metar.getWindSpeedInMPS());
        } catch (Exception e) {
            log.log(Level.ERROR, e);
            throw new RequestException(e.getMessage());
        }

        try {
            entity.setSubscription(subscription.get());
            return metarRepository.save(entity);
        } catch (Exception ex) {
            log.log(Level.ERROR, ex);
            throw new RequestException(ex.getMessage());
        }
    }
}
