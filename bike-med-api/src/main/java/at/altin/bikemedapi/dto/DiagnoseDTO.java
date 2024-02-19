package at.altin.bikemedapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

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

    public long countTrueBooleans() {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> field.getType() == boolean.class)
                .mapToLong(field -> {
                    try {
                        return field.getBoolean(this) ? 1 : 0;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Fehler beim Zugriff auf das Feld", e);
                    }
                })
                .sum();
    }
}
