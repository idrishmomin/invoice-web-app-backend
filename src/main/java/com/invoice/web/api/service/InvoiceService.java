package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class InvoiceService {

    public Response<Object> invoices() {
        return null;
    }

    public Response<Object> getInvoiceDetails(String id) {
        return null;
    }


    public Response<Object> createInvoice(CreateInvoiceRequest createInvoiceRequest) {
        return null;
    }
}
