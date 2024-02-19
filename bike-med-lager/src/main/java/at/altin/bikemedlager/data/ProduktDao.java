package at.altin.bikemedlager.data;

import at.altin.bikemedlager.model.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduktDao extends JpaRepository<Produkt, Long> {
    Optional<Produkt> findByName(String name);
}
