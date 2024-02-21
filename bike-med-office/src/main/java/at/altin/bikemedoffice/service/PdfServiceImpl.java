package at.altin.bikemedoffice.service;

import at.altin.bikemedcommons.exception.BikeMedException;
import at.altin.bikemedoffice.data.OfficeDataDao;
import at.altin.bikemedoffice.model.OfficeData;
import at.altin.bikemedoffice.model.Product;
import at.altin.bikemedoffice.service.api.PdfService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {
    public static final String LINE = "------------------------------";
    private final OfficeDataDao officeDataDao;

    public PdfServiceImpl(OfficeDataDao officeDataDao) {
        this.officeDataDao = officeDataDao;
    }

    public byte[] generatePdf(UUID officeDataId) {
        try {
            OfficeData officeData = officeDataDao.findById(officeDataId)
                    .orElseThrow(() -> new IllegalArgumentException("Office data not found"));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            addTitle(document);

            addContent(document, officeData);

            document.close();

            log.info("PDF generated successfully.");

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error generating PDF", e);
            throw new BikeMedException("Error generating PDF");
        }
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph titleParagraph = new Paragraph("BikeMed Diagnose Report");
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(20);
        document.add(titleParagraph);
    }

    private void addContent(Document document, OfficeData officeData) throws DocumentException {
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
        document.add(new Paragraph("\nBikeMed Diagnose:"));
        document.add(new Paragraph(LINE));
        addCheckboxItem(document, "Plattenreparatur", officeData.isPlattenReparatur());
        addCheckboxItem(document, "Ventil", officeData.isVentil());
        addCheckboxItem(document, "Bremsen", officeData.isBremsen());
        addCheckboxItem(document, "Schaltung", officeData.isSchaltung());
        addCheckboxItem(document, "Beleuchtung vorne", officeData.isBeleuchtungVorne());
        addCheckboxItem(document, "Beleuchtung hinten", officeData.isBeleuchtungHinten());
        addCheckboxItem(document, "Reflector", officeData.isReflector());
        addCheckboxItem(document, "Federung", officeData.isFederung());
        addCheckboxItem(document, "Rahmen", officeData.isRahmen());
        addCheckboxItem(document, "Gabel", officeData.isGabel());
        addCheckboxItem(document, "Kettenantrieb", officeData.isKettenantrieb());
        addCheckboxItem(document, "Elektrische Komponenten", officeData.isElektrischeKomponenten());
        addCheckboxItem(document, "Sonstige Probleme", officeData.isSonstigeProbleme());
        document.add(new Paragraph("Custom Note: " + officeData.getCustomNote()));
        document.add(new Paragraph("Fehler Anzahl: " + officeData.getFehlerAnzahl()));
    }

    private void addCheckboxItem(Document document, String label, boolean checked) throws DocumentException {
        Paragraph paragraph = new Paragraph(label + ": " + (checked ? "In Ordnung" : "Nicht in Ordnung"));
        document.add(paragraph);
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
