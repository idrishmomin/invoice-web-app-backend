package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.dto.response.UserResponseDto;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final InvoiceService invoiceService;
    private final GenerateInvoiceService generateInvoiceService;

    @GetMapping(value = "/user-details/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserResponseDto>> userDetails(@PathVariable String email) {
        log.info("Get UserDetails Request");
        return userService.userDetails(email);
    }

    @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> invoices() {
        log.info("Get Invoices request ");
        return invoiceService.invoices();
    }

    @GetMapping(value = "/invoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> getInvoiceDetails(@PathVariable String id) {
        log.info("Get Invoice details");
        return invoiceService.getInvoiceDetails(id);
    }


    @GetMapping(value = "/generateinvoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String generatePDFInvoice(@PathVariable String id) throws IOException {
        log.info("Generate Invoice PDF");
        return generateInvoiceService.createInvoice(id);
    }

    @GetMapping(value = "/otherdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> getOtherDetails() {
        log.info("Get Other details");
        return invoiceService.otherDetails();
    }


    @PostMapping(value = "/createinvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createinvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        log.info("Create Invoice request: {}", createInvoiceRequest);
        return invoiceService.createInvoice(createInvoiceRequest);
    }
}
