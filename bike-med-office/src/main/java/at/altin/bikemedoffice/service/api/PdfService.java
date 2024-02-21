package at.altin.bikemedoffice.service.api;

import java.util.UUID;

public interface PdfService {

    byte[] generatePdf(UUID officeDataId);
}
