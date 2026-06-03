package com.example.spacecenter.service;

import com.example.spacecenter.domain.outbox.Outbox;
import com.example.spacecenter.domain.satellite.Satellite;
import com.example.spacecenter.dto.SatelliteEvent;
import com.example.spacecenter.repository.OutboxRepository;
import com.example.spacecenter.repository.SatelliteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SatelliteService {

    private final SatelliteRepository satelliteRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Satellite createSatellite(Satellite satellite) {
        Satellite saved = satelliteRepository.save(satellite);
        createOutboxEvent(saved.getId().toString(), "CREATED", saved);
        log.info("Satellite created and outbox event stored: {}", saved.getId());
        return saved;
    }

    @Transactional
    public void deleteSatellite(Long id) {
        satelliteRepository.deleteById(id);
        createOutboxEvent(id.toString(), "DELETED", null);
        log.info("Satellite deleted and outbox event stored: {}", id);
    }

    public List<Satellite> getAllSatellites() {
        return satelliteRepository.findAll();
    }

    public Satellite getSatellite(Long id) {
        return satelliteRepository.findById(id).orElseThrow(() -> new RuntimeException("Satellite not found"));
    }

    private void createOutboxEvent(String aggregateId, String eventType, Object payload) {
        try {
            String eventId = UUID.randomUUID().toString();
            SatelliteEvent event = new SatelliteEvent(eventId, aggregateId, eventType, payload);
            String jsonPayload = objectMapper.writeValueAsString(event);
            Outbox outbox = new Outbox(aggregateId, eventType, jsonPayload);
            outboxRepository.save(outbox);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize outbox event", e);
        }
    }
}
