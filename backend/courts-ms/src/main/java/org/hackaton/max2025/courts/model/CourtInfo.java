package org.hackaton.max2025.courts.model;

import org.hackaton.max2025.courts.model.util.CourtType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "court_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lon", nullable = false)
    private Double lon;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtType type;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @Column(name = "description")
    private String description;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToOne(mappedBy = "courtInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Courts court;
}