package at.altin.bikemedwerkstatt.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Mitarbeiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "gehalt", nullable = false)
    private Double gehalt;

}
