package at.altin.bikemedwerkstatt.data;

import at.altin.bikemedwerkstatt.model.Mitarbeiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MitarbeiterDao extends JpaRepository<Mitarbeiter, Long> {
}
