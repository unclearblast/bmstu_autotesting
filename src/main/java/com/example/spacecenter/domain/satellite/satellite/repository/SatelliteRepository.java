package com.example.spacecenter.repository;

import com.example.spacecenter.domain.satellite.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
}
