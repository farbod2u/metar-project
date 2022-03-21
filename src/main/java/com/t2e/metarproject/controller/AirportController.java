package com.t2e.metarproject.controller;

import com.t2e.metarproject.entity.Metar;
import com.t2e.metarproject.service.MetarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airport")
public class AirportController {

    private final MetarService metarService;

    @GetMapping("/{icaoCode}/METAR")
    public ResponseEntity<Metar> getLastMetarByIcaoCode(@PathVariable String icaoCode) {
        return new ResponseEntity<>(metarService.getLastMetarByIcaoCode(icaoCode), HttpStatus.OK);
    }

    @PostMapping("/{icaoCode}/METAR")
    public ResponseEntity<Metar> save(@PathVariable String icaoCode, @RequestBody Metar entity) {
        return new ResponseEntity<>(metarService.save(icaoCode, entity), HttpStatus.OK);
    }
}
