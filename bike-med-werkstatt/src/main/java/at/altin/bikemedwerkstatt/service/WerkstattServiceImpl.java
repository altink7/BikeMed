package at.altin.bikemedwerkstatt.service;


import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.WerkstattEventDTO;
import at.altin.bikemedwerkstatt.data.KonfigurationEntityDao;
import at.altin.bikemedwerkstatt.model.KonfigurationEntity;
import at.altin.bikemedwerkstatt.service.api.WerkstattService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WerkstattServiceImpl implements WerkstattService {
    private final KonfigurationEntityDao konfigurationEntityDao;

    @Value("${queue.werkstatt.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public WerkstattServiceImpl(KonfigurationEntityDao konfigurationEntityDao) {
        this.konfigurationEntityDao = konfigurationEntityDao;
    }

    @Override
    public WerkstattEventDTO buildWerkstattEvent(DiagnoseEventDTO event) {
        WerkstattEventDTO werkstattEventDTO = new WerkstattEventDTO();
        if(event.countFalseBooleans() <= 3) {
            werkstattEventDTO.setAnzahlMitarbeiter(1);
        } else {
            werkstattEventDTO.setAnzahlMitarbeiter(2);
        }

        werkstattEventDTO.setDiagnoseEventDTO(event);
        werkstattEventDTO.setStundenSatz(getKonfigurationEntity().getStundenSatz());
        werkstattEventDTO.setEventId(event.getEventId());
        werkstattEventDTO.setWerkstattName(getKonfigurationEntity().getWerkstattName());
        werkstattEventDTO.setFrom(queueName);
        werkstattEventDTO.setTo(dispatcherQueueName);

        return werkstattEventDTO;
    }

    private KonfigurationEntity getKonfigurationEntity() {
        return konfigurationEntityDao.findAll().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Konfiguration not found"));
    }
}
