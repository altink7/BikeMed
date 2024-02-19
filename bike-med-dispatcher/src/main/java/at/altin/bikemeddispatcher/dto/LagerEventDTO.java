package at.altin.bikemeddispatcher.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class LagerEventDTO extends EventDTO implements Serializable {
    private List<Map<String, Double>> products;
}
