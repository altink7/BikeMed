package at.altin.bikemedwerkstatt.data;

import at.altin.bikemedwerkstatt.model.KonfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KonfigurationEntityDao extends JpaRepository<KonfigurationEntity, Long> {
}
