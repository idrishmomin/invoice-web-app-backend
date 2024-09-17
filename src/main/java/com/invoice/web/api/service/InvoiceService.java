package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.infrastructure.utils.CommonUtils;
import com.invoice.web.persistence.model.Invoice;
import com.invoice.web.persistence.repositories.InvoiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Response<Object> invoices() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        log.info("Invoice List Size : {}", invoiceList.size());
        return Response.builder().response(invoiceList).build();
    }

    public Response<Object> getInvoiceDetails(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (null == invoice) {
            log.info("Invoice with invoiceNumber : {} does not exist", invoiceNumber);
        }
        return Response.builder().response(invoice).build();
    }


    public Response<Object> createInvoice(CreateInvoiceRequest request) {

        if (null != request.getInvoiceNumber()) {
            Invoice invoice = invoiceRepository.findByInvoiceNumber(request.getInvoiceNumber());
            if (null == invoice) {
                log.info("Invoice with invoiceNumber : {} does not exist", request.getInvoiceNumber());
                return Response.builder().response("Invoice with invoiceId does not exist ").build();
            } else {
                invoice.setRateOfSAR(request.getRateOfSAR());
                invoice.setVendorInvoiceDate(request.getVendorInvoiceDate());
                invoice.setInvoiceUpdatedDate(LocalDateTime.now());

                invoice.setTotal(request.getTotal());
                invoice.setBankDetails(request.getBankDetails());
                invoice.setAccountDue(request.getAccountDue());
                invoice.setEditedBy(request.getUpdatedBy());
                invoiceRepository.save(invoice);
                return Response.builder().response(invoice).build();
            }
        } else {
            String invoiceNumber = CommonUtils.generateInvoiceNumber();
            log.info("Invoice Generating with invoice Number : {}", invoiceNumber);
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(invoiceNumber);
            invoice.setRateOfSAR(request.getRateOfSAR());
            invoice.setVendorInvoiceDate(request.getVendorInvoiceDate());
            invoice.setInvoiceCreatedDate(LocalDateTime.now());

            invoice.setTotal(request.getTotal());
            invoice.setBankDetails(request.getBankDetails());
            invoice.setAccountDue(request.getAccountDue());
            invoice.setSubmitter(request.getSubmitter());

            invoiceRepository.save(invoice);
            return Response.builder().response(invoice).build();
        }

    }
}
