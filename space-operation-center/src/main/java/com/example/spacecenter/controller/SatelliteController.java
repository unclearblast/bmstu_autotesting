package com.example.spacecenter.controller;

import com.example.spacecenter.domain.satellite.Satellite;
import com.example.spacecenter.service.SatelliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/satellites")
@RequiredArgsConstructor
public class SatelliteController {

    private final SatelliteService satelliteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Satellite create(@RequestBody Satellite satellite) {
        return satelliteService.createSatellite(satellite);
    }

    @GetMapping
    public List<Satellite> list() {
        return satelliteService.getAllSatellites();
    }

    @GetMapping("/{id}")
    public Satellite get(@PathVariable Long id) {
        return satelliteService.getSatellite(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        satelliteService.deleteSatellite(id);
    }
}
