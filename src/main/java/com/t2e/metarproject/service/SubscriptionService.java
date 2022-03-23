package com.t2e.metarproject.service;

import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

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

    public List<Subscription> getAll(Subscription example) {
        example.setIcaoCode(example.getIcaoCode().toUpperCase());
        return subscriptionRepository.findAll(
                where(predicateIcaoCode(example.getIcaoCode()))
                        .and(predicateActive(example.getActive()))
        );
    }

    private Specification<Subscription> predicateIcaoCode(String icaoCode) {
        return (root, query, criteriaBuilder) -> {
            if (icaoCode != null)
                return criteriaBuilder.like(root.get("icaoCode"), "%" + icaoCode + "%");
            else
                return null;
        };
    }

    private Specification<Subscription> predicateActive(Integer active) {
        return (root, query, criteriaBuilder) -> {
            if (active != null)
                return criteriaBuilder.equal(root.get("active"), active);
            else
                return null;
        };
    }

    public Subscription delete(String icaoCode) {
        Optional<Subscription> entity = subscriptionRepository.getByIcaoCode(icaoCode);
        if (entity.isPresent()) {
            subscriptionRepository.delete(entity.get());
            return entity.get();
        } else
            throw new EntityNotFoundException("ICAO Code not found");
    }

    private void checkActiveRange(Integer active) {
        if (active < 0 || active > 1)
            throw new RequestException("Valid values for Active are 0 and 1.");
    }

    @Transactional(rollbackOn = Exception.class)
    public Subscription enable(String icaoCode, Integer active) {
        checkActiveRange(active);

        Optional<Subscription> entity = subscriptionRepository.getByIcaoCode(icaoCode);
        if (entity.isPresent()) {
            entity.get().setActive(active);

            return entity.get();
        } else
            throw new EntityNotFoundException("ICAO Code not found");
    }
}
