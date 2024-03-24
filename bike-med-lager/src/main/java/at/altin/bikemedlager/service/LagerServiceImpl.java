package at.altin.bikemedlager.service;

import at.altin.bikemed.commons.config.QueueConfig;
import at.altin.bikemed.commons.dto.DiagnoseEventDTO;
import at.altin.bikemed.commons.dto.LagerEventDTO;
import at.altin.bikemedlager.data.ProduktDao;
import at.altin.bikemedlager.model.Produkt;
import at.altin.bikemedlager.service.api.LagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Slf4j
public class LagerServiceImpl implements LagerService {
    private final ProduktDao produktDao;

    @Override
    public LagerEventDTO buildLagerEvent(DiagnoseEventDTO event) {
        LagerEventDTO eventDTO = new LagerEventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setFrom(QueueConfig.QUEUE_LAGER);
        eventDTO.setTo(QueueConfig.QUEUE_DISPATCHER);


        List<Boolean> properties = getDiagnoseBooleans(event);

        Map<String, Double> products = IntStream.range(0, properties.size())
                .filter(properties::get)
                .mapToObj(DiagnoseEventDTO::getPropertyNameAtIndex)
                .flatMap(propertyName -> produktDao.findByName(propertyName).stream())
                .collect(Collectors.toMap(Produkt::getName, Produkt::getPrice));

        eventDTO.setProducts(products);

        return eventDTO;
    }

    private static List<Boolean> getDiagnoseBooleans(DiagnoseEventDTO event) {
        return Arrays.asList(
                !event.isPlattenReparatur(),
                !event.isVentil(),
                !event.isBremsen(),
                !event.isSchaltung(),
                !event.isBeleuchtungVorne(),
                !event.isBeleuchtungHinten(),
                !event.isReflector(),
                !event.isFederung(),
                !event.isRahmen(),
                !event.isGabel(),
                !event.isKettenantrieb(),
                !event.isElektrischeKomponenten()
        );
    }
}
