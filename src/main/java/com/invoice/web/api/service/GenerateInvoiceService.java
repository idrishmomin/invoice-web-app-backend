package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.persistence.model.Invoice;
import com.invoice.web.persistence.model.Signature;
import com.invoice.web.persistence.repositories.InvoiceRepository;
import com.invoice.web.persistence.repositories.SignatureRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenerateInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SignatureRepository signatureRepository;

    public GenerateInvoiceService(InvoiceRepository invoiceRepository, SignatureRepository signatureRepository) {
        this.invoiceRepository = invoiceRepository;
        this.signatureRepository = signatureRepository;
    }

    public String createInvoice(String invoiceId) throws IOException {

        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceId);
        List<Signature> signatureList = signatureRepository.findAll();

        if (null == invoice) {
            log.info("Invoice not fod for Invoice Number : {}", invoiceId);
        }

        InputStream templateStream = GenerateInvoiceService.class.getClassLoader().getResourceAsStream("invoice_template.html");

        if (templateStream == null) {
            throw new IllegalArgumentException("invoice_template.html not found in resources folder");
        }

        String htmlTemplate = new BufferedReader(
                new InputStreamReader(templateStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));


//        String htmlTemplate = new String(Files.readAllBytes(Paths.get("invoice_template.html")));


        // Step 2: Replace placeholders with actual data
        String invoiceHtml = htmlTemplate
                .replace("${invoiceNumber}", invoice.getInvoiceNumber())
                .replace("${invoiceCreatedDate}", invoice.getInvoiceCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .replace("${invoiceUpdatedDate}", "N/A")
                .replace("${submitterName}", invoice.getSubmitter().getSubmitterName())
                .replace("${department}", invoice.getSubmitter().getDepartment())
                .replace("${paymentType}", invoice.getAccountDetails().getPaymentType())
                .replace("${accountType}", invoice.getAccountDetails().getAccountType())

                .replace("${subTotal}", invoice.getTotal().getSubTotal())
                .replace("${adjustment}", invoice.getTotal().getAdjustments())
                .replace("${grandTotal}", invoice.getTotal().getGrandTotal());


        StringBuilder itemsHtml = new StringBuilder();
        int srNo = 0;
        for (CreateInvoiceRequest.Item item : invoice.getItems()) {
            srNo = srNo + 1;
            itemsHtml.append("<tr>")
                    .append("<td>").append(srNo).append("</td>")
                    .append("<td>").append(item.getVendorInvoiceRef()).append("</td>")
                    .append("<td>").append(item.getVendorId()).append("</td>")
                    .append("<td>").append(item.getVendorName()).append("</td>")
                    .append("<td>").append(item.getVendorInvoiceDate()).append("</td>")
                    .append("<td>").append(item.getInvoiceAmount()).append("</td>")
                    .append("<td>").append(item.getRateOfSAR()).append("</td>")
                    .append("<td>").append(item.getRecurring()).append("</td>")
                    .append("<td>").append(item.getCostCode()).append("</td>")
                    .append("<td>").append(item.getExpenseType()).append("</td>")
                    .append("<td>").append(item.getCurrency()).append("</td>")
                    .append("<td>").append(item.getDescription()).append("</td>")
                    .append("</tr>");
        }

        invoiceHtml = invoiceHtml.replace("${items}", itemsHtml.toString());

        // Step 5: Generate dynamic signatures
//        StringBuilder signaturesHtml = new StringBuilder();
//        for (Signature signature : signatureList) {
//            signaturesHtml.append("<p>").append(signature.getDepartment());
//            signaturesHtml.append("<p>");
//            signaturesHtml.append("<p>").append(signature.getName()).append(": _____________________</p>");
//        }

        StringBuilder signaturesHtml = new StringBuilder();
        for (Signature signature : signatureList) {
            signaturesHtml.append("<div style='border: 1px solid black; padding: 10px; margin-bottom: 10px;'>"); // Add a border and padding to each signature
            signaturesHtml.append("<p><strong>Signature By ").append(signature.getDepartment()).append("</strong>").append(" : <h4>").append(signature.getName()).append("</h4>").append("</p>");
            signaturesHtml.append("<p><strong>Date: </strong> _____________________</p>");
            signaturesHtml.append("<p><strong>Signature: </strong> _____________________</p>");
            signaturesHtml.append("</div>"); // Close the border div
        }

        // Step 6: Replace the ${signatures} placeholder with the dynamically generated signature lines
        invoiceHtml = invoiceHtml.replace("${signatures}", signaturesHtml.toString());

        // Step 5: Convert the HTML to PDF using iText

        FileOutputStream pdfStream = new FileOutputStream(new File("invoice.pdf"));
        PdfWriter writer = new PdfWriter(pdfStream);
        PdfDocument pdfDocument = new PdfDocument(writer);

        // Set the document size to A4
        pdfDocument.setDefaultPageSize(PageSize.A4);
        // Prepare converter properties
        ConverterProperties properties = new ConverterProperties();

        // Convert HTML to PDF
        HtmlConverter.convertToPdf(new ByteArrayInputStream(invoiceHtml.getBytes(StandardCharsets.UTF_8)), pdfStream, properties);

        System.out.println("Invoice PDF generated successfully.");
        return invoiceHtml;
    }

}




