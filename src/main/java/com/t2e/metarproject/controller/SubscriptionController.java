package com.t2e.metarproject.controller;

import com.t2e.metarproject.entity.Subscription;
import com.t2e.metarproject.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAll() {
        return new ResponseEntity<>(subscriptionService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subscription> save(@RequestBody Subscription entity) {
        return new ResponseEntity<>(subscriptionService.save(entity.getIcaoCode().trim()), HttpStatus.OK);
    }

    @DeleteMapping("/{icaoCode}")
    public ResponseEntity<Subscription> delete(@PathVariable String icaoCode){
        return new ResponseEntity<>(subscriptionService.delete(icaoCode), HttpStatus.OK);
    }

    @PutMapping("/{icaoCode}")
    public ResponseEntity<Subscription> active(@PathVariable String icaoCode, @RequestBody Subscription entity){
        return new ResponseEntity<>(subscriptionService.enable(icaoCode, entity.getActive()), HttpStatus.OK);
    }
}
