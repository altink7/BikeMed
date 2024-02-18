package at.altin.bikemedapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class DiagnoseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
