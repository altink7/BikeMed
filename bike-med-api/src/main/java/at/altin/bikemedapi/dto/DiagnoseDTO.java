package at.altin.bikemedapi.dto;

import lombok.Data;

@Data
public class DiagnoseDTO {
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
}
