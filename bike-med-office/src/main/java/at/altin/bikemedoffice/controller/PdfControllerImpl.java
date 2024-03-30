package at.altin.bikemedoffice.controller;

import at.altin.bikemedoffice.controller.api.PdfController;
import at.altin.bikemedoffice.service.api.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pdf")
@CrossOrigin
public class PdfControllerImpl implements PdfController {
    private final PdfService pdfService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable UUID id) {
        byte[] pdfBytes = pdfService.generatePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "BikeMed_Diagnose_Report.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
