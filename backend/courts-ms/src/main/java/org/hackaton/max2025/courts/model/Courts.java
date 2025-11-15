package org.hackaton.max2025.courts.model;

import jakarta.persistence.*;
import lombok.*;
import org.hackaton.max2025.courts.model.util.CourtType;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courts {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lon", nullable = false)
    private Double lon;

    @Enumerated(EnumType.STRING)
    @Column(name = "court_type", nullable = false)
    private CourtType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_info_id", nullable = false)
    private CourtInfo courtInfo;
}