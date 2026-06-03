package com.example.telemetry.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class SatelliteStorageService {
    private final ConcurrentMap<String, Boolean> storage = new ConcurrentHashMap<>();

    public void addSatellite(String id) {
        storage.put(id, true);
        log.info("Satellite {} added to telemetry storage", id);
    }

    public void removeSatellite(String id) {
        storage.remove(id);
        log.info("Satellite {} removed from telemetry storage", id);
    }
}
