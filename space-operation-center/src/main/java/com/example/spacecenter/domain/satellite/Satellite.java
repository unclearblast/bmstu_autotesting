package com.example.spacecenter.domain.satellite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "satellites")
@Getter
@Setter
@NoArgsConstructor
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SatelliteState state;

    public Satellite(String name, SatelliteState state) {
        this.name = name;
        this.state = state;
    }
}
