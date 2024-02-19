package at.altin.bikemedwerkstatt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class KonfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "stunden-satz", nullable = false)
    private Double stundenSatz;

    @Column(name = "werkstatt-name", nullable = false)
    private String werkstattName;
}
