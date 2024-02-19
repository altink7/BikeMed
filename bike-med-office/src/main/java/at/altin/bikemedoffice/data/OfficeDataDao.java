package at.altin.bikemedoffice.data;

import at.altin.bikemedoffice.model.OfficeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfficeDataDao extends JpaRepository<OfficeData, UUID> {
}
