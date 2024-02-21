package at.altin.bikemeddispatcher.dto;

import at.altin.bikemedcommons.dto.EventDTO;
import at.altin.bikemedcommons.exception.BikeMedException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DiagnoseEventDTO extends EventDTO implements Serializable {
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

    public long countFalseBooleans() {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> field.getType() == boolean.class)
                .mapToLong(field -> {
                    try {
                        return field.getBoolean(this) ? 0 : 1;
                    } catch (IllegalAccessException e) {
                        throw new BikeMedException("Fehler beim Zugriff auf das Feld", e);
                    }
                })
                .sum();
    }

    public static String getPropertyNameAtIndex(int index) {
        String[] propertyNames = {
                "plattenReparatur",
                "ventil",
                "bremsen",
                "schaltung",
                "beleuchtungVorne",
                "beleuchtungHinten",
                "reflector",
                "federung",
                "rahmen",
                "gabel",
                "kettenantrieb",
                "elektrischeKomponenten"};

        if (index >= 0 && index < propertyNames.length) {
            return propertyNames[index];
        } else {
            throw new IllegalArgumentException("Ungültiger Index für Eigenschaften namen: " + index);
        }
    }

}
