package com.t2e.metarproject.service;

import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription save(String icaoCode) {
        if (!icaoCode.matches("[A-Z&&[^IJXQ]]{1}[A-Z]{3}"))
            throw new RequestException("ICAO code is incorrect.");

        Subscription entity = new Subscription(icaoCode, null, 1);
        try {
            return subscriptionRepository.save(entity);
        } catch (Exception ex) {
            throw new RequestException(ex.getMessage());
        }
    }

    /*public Subscription getByIcaoCode(String icaoCode){
        return subscriptionRepository.getByIcaoCode(icaoCode);
    }*/

    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    public Subscription delete(String icaoCode) {
        Optional<Subscription> entity = subscriptionRepository.getByIcaoCode(icaoCode);
        if (entity.isPresent()) {
            subscriptionRepository.delete(entity.get());
            return entity.get();
        } else
            throw new EntityNotFoundException("ICAO Code not found");
    }

}
