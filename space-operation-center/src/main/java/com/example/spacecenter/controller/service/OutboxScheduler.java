package com.example.spacecenter.service;

import com.example.spacecenter.domain.outbox.Outbox;
import com.example.spacecenter.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${outbox.scheduler.fixed-delay:5000}")
    private long fixedDelay;

    @Scheduled(fixedDelayString = "${outbox.scheduler.fixed-delay:5000}")
    @Transactional
    public void processOutbox() {
        List<Outbox> pending = outboxRepository.findByStatus(Outbox.OutboxStatus.PENDING);
        if (pending.isEmpty()) return;
        log.info("Processing {} pending outbox records", pending.size());

        for (Outbox record : pending) {
            try {
                kafkaTemplate.send("satellite-events", record.getAggregateId(), record.getPayload());
                outboxRepository.updateStatus(record.getId(), Outbox.OutboxStatus.SENT);
                log.info("Outbox record {} sent to Kafka", record.getId());
            } catch (Exception e) {
                log.error("Failed to send outbox record {}", record.getId(), e);
            }
        }
    }
}
