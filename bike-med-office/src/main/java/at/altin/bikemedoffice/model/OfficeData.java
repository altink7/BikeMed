package at.altin.bikemedoffice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class OfficeData {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "officeData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    private boolean plattenReparatur;
    private boolean ventil;
    private boolean bremsen;
    private boolean schaltung;
    private boolean beleuchtungVorne;
    private boolean beleuchtungHinten;
    private boolean reflector;
    private boolean federung;
    private boolean rahmen;
    private boolean gabel;
    private boolean kettenantrieb;
    private boolean elektrischeKomponenten;
    private boolean sonstigeProbleme;
    private String customNote;
    private long fehlerAnzahl;
    private String werkstattName;
    private int anzahlMitarbeiter;
    private double stundenSatz;
}
