package at.altin.bikemeddispatcher.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class DiagnoseEventDTO implements Serializable {
    UUID eventId;
    DiagnoseDTO diagnoseDTO;
    String from;
    String to;
}
