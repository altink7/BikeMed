package at.altin.bikemedoffice.service;

import at.altin.bikemed.commons.dto.OfficeDataEventDTO;
import at.altin.bikemedoffice.data.OfficeDataDao;
import at.altin.bikemedoffice.data.ProductDao;
import at.altin.bikemedoffice.model.OfficeData;
import at.altin.bikemedoffice.model.Product;
import at.altin.bikemedoffice.service.api.OfficeDataCollectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class OfficeDataCollectorServiceImpl implements OfficeDataCollectorService {
    private final OfficeDataDao officeDataDao;
    private final ProductDao productDao;

    @Transactional
    public void collectOfficeData(OfficeDataEventDTO event) {
        log.info("Saving office data: {}", event);
        OfficeData officeData = officeDataDao.findById(event.getEventId()).orElse(new OfficeData());
        officeData.setId(event.getEventId());
        setWerkstattEventData(event, officeData);
        setLagerEventData(event, officeData);

        officeDataDao.save(officeData);
    }

    private void setLagerEventData(OfficeDataEventDTO event, OfficeData officeData) {
        if (event.getLagerEventDTO() != null && event.getLagerEventDTO().getProducts() != null) {
            event.getLagerEventDTO().getProducts().forEach((name, price) -> {
                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setOfficeData(officeData);
                productDao.save(product);
            });
        }
    }

    private void setWerkstattEventData(OfficeDataEventDTO event, OfficeData officeData) {
        if (event.getWerkstattEventDTO() != null) {
            officeData.setPlattenReparatur(event.getWerkstattEventDTO().getDiagnoseEventDTO().isPlattenReparatur());
            officeData.setVentil(event.getWerkstattEventDTO().getDiagnoseEventDTO().isVentil());
            officeData.setBremsen(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBremsen());
            officeData.setSchaltung(event.getWerkstattEventDTO().getDiagnoseEventDTO().isSchaltung());
            officeData.setBeleuchtungVorne(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBeleuchtungVorne());
            officeData.setBeleuchtungHinten(event.getWerkstattEventDTO().getDiagnoseEventDTO().isBeleuchtungHinten());
            officeData.setReflector(event.getWerkstattEventDTO().getDiagnoseEventDTO().isReflector());
            officeData.setFederung(event.getWerkstattEventDTO().getDiagnoseEventDTO().isFederung());
            officeData.setRahmen(event.getWerkstattEventDTO().getDiagnoseEventDTO().isRahmen());
            officeData.setGabel(event.getWerkstattEventDTO().getDiagnoseEventDTO().isGabel());
            officeData.setKettenantrieb(event.getWerkstattEventDTO().getDiagnoseEventDTO().isKettenantrieb());
            officeData.setElektrischeKomponenten(event.getWerkstattEventDTO().getDiagnoseEventDTO().isElektrischeKomponenten());
            officeData.setSonstigeProbleme(event.getWerkstattEventDTO().getDiagnoseEventDTO().isSonstigeProbleme());
            officeData.setCustomNote(event.getWerkstattEventDTO().getDiagnoseEventDTO().getCustomNote());
            officeData.setFehlerAnzahl(event.getWerkstattEventDTO().getDiagnoseEventDTO().countFalseBooleans());
            officeData.setWerkstattName(event.getWerkstattEventDTO().getWerkstattName());
            officeData.setAnzahlMitarbeiter(event.getWerkstattEventDTO().getAnzahlMitarbeiter());
            officeData.setStundenSatz(event.getWerkstattEventDTO().getStundenSatz());
        }
    }
}
