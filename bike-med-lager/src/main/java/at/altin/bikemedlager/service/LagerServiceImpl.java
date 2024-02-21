package at.altin.bikemedlager.service;

import at.altin.bikemeddispatcher.dto.DiagnoseEventDTO;
import at.altin.bikemeddispatcher.dto.LagerEventDTO;
import at.altin.bikemedlager.data.ProduktDao;
import at.altin.bikemedlager.model.Produkt;
import at.altin.bikemedlager.service.api.LagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class LagerServiceImpl implements LagerService {
    private final ProduktDao produktDao;

    @Value("${queue.lager.name}")
    private String queueName;

    @Value("${queue.dispatcher.name}")
    private String dispatcherQueueName;

    public LagerServiceImpl(ProduktDao produktDao) {
        this.produktDao = produktDao;
    }

    @Override
    public LagerEventDTO buildLagerEvent(DiagnoseEventDTO event) {
        LagerEventDTO eventDTO = new LagerEventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setFrom(queueName);
        eventDTO.setTo(dispatcherQueueName);


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
