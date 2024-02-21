package at.altin.bikemeddispatcher.dto;

import at.altin.bikemedcommons.dto.EventDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LagerEventDTO extends EventDTO implements Serializable {
    private Map<String, Double> products;
}
