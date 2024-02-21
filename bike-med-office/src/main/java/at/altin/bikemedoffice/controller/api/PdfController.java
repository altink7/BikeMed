package at.altin.bikemedoffice.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface PdfController {

    ResponseEntity<byte[]> generatePdf(@PathVariable UUID id);
}
