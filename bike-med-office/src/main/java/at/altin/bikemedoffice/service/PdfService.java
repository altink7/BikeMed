package at.altin.bikemedoffice.service;

import at.altin.bikemedoffice.data.OfficeDataDao;
import at.altin.bikemedoffice.model.OfficeData;
import at.altin.bikemedoffice.model.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class PdfService {
    public static final String LINE = "------------------------------";
    private final OfficeDataDao officeDataDao;

    public PdfService(OfficeDataDao officeDataDao) {
        this.officeDataDao = officeDataDao;
    }

    public byte[] generatePdf(UUID officeDataId) {
        try {
            OfficeData officeData = officeDataDao.findById(officeDataId)
                    .orElseThrow(() -> new IllegalArgumentException("Office data not found"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            addContent(document, officeData);
            document.close();

            log.info("PDF generated successfully.");

            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Error generating PDF", e);
            throw new RuntimeException("Error generating PDF");
        }
    }

    private void addContent(Document document, OfficeData officeData) throws DocumentException {
        document.add(new Paragraph("ID: " + officeData.getId()));
        document.add(new Paragraph("\nBikeMed Diagnose:"));
        document.add(new Paragraph(LINE));
        addDiagnose(document, officeData);
        addWerkstattBereich(document, officeData);
        addProducts(document, officeData);
        document.add(new Paragraph("\nTotal: $" + calculateTotalCost(officeData)));
    }

    private void addProducts(Document document, OfficeData officeData) throws DocumentException {
        if (officeData.getProducts() != null && !officeData.getProducts().isEmpty()) {
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Produkt");
            table.addCell("Preis");

            for (Product product : officeData.getProducts()) {
                table.addCell(product.getName());
                table.addCell(String.valueOf(product.getPrice()));
            }

            document.add(table);
        }
    }

    private void addDiagnose(Document document, OfficeData officeData) throws DocumentException {
        document.add(new Paragraph("Plattenreparatur: " + (officeData.isPlattenReparatur() ? "✔" : "✘")));
        document.add(new Paragraph("Ventil: " + (officeData.isVentil() ? "✔" : "✘")));
        document.add(new Paragraph("Bremsen: " + (officeData.isBremsen() ? "✔" : "✘")));
        document.add(new Paragraph("Schaltung: " + (officeData.isSchaltung() ? "✔" : "✘")));
        document.add(new Paragraph("Beleuchtung vorne: " + (officeData.isBeleuchtungVorne() ? "✔" : "✘")));
        document.add(new Paragraph("Beleuchtung hinten: " + (officeData.isBeleuchtungHinten() ? "✔" : "✘")));
        document.add(new Paragraph("Reflector: " + (officeData.isReflector() ? "✔" : "✘")));
        document.add(new Paragraph("Federung: " + (officeData.isFederung() ? "✔" : "✘")));
        document.add(new Paragraph("Rahmen: " + (officeData.isRahmen() ? "✔" : "✘")));
        document.add(new Paragraph("Gabel: " + (officeData.isGabel() ? "✔" : "✘")));
        document.add(new Paragraph("Kettenantrieb: " + (officeData.isKettenantrieb() ? "✔" : "✘")));
        document.add(new Paragraph("Elektrische Komponenten: " + (officeData.isElektrischeKomponenten() ? "✔" : "✘")));
        document.add(new Paragraph("Sonstige Probleme: " + (officeData.isSonstigeProbleme() ? "✔" : "✘")));
        document.add(new Paragraph("Custom Note: " + officeData.getCustomNote()));
        document.add(new Paragraph("Fehler Anzahl: " + officeData.getFehlerAnzahl()));
    }

    private void addWerkstattBereich(Document document, OfficeData officeData) throws DocumentException {
        document.add(new Paragraph("\nWerkstatt Bereich:"));
        document.add(new Paragraph(LINE));
        document.add(new Paragraph("Werkstatt Name: " + officeData.getWerkstattName()));
        document.add(new Paragraph("Anzahl Mitarbeiter: " + officeData.getAnzahlMitarbeiter()));
        document.add(new Paragraph("Stundensatz: $" + officeData.getStundenSatz()));
        document.add(new Paragraph("\nKostenaufstellung:"));
        document.add(new Paragraph(LINE));
    }

    private double calculateTotalCost(OfficeData officeData) {
        double totalCost = 0.0;

        if (officeData.getProducts() != null && !officeData.getProducts().isEmpty()) {
            for (Product product : officeData.getProducts()) {
                totalCost += product.getPrice();
            }
        }

        return totalCost;
    }
}
