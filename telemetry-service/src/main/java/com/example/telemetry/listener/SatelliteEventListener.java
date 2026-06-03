package com.example.telemetry.listener;

import com.example.telemetry.domain.inbox.Inbox;
import com.example.telemetry.dto.SatelliteEvent;
import com.example.telemetry.repository.InboxRepository;
import com.example.telemetry.service.SatelliteStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SatelliteEventListener {

    private final InboxRepository inboxRepository;
    private final SatelliteStorageService storageService;

    @KafkaListener(topics = "satellite-events", groupId = "telemetry-group")
    @Transactional
    public void handle(SatelliteEvent event) {
        if (inboxRepository.existsById(event.getEventId())) {
            log.info("Duplicate event {} ignored", event.getEventId());
            return;
        }
        inboxRepository.save(new Inbox(event.getEventId(), event.getAggregateId(), event.getEventType()));

        switch (event.getEventType()) {
            case "CREATED" -> storageService.addSatellite(event.getAggregateId());
            case "DELETED" -> storageService.removeSatellite(event.getAggregateId());
            default -> log.warn("Unknown event type: {}", event.getEventType());
        }
        log.info("Processed event {} for satellite {}", event.getEventId(), event.getAggregateId());
    }
}
