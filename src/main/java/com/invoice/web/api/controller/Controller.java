package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    private final UserService userService;
    private final InvoiceService invoiceService;
    private final GenerateInvoiceService generateInvoiceService;

    public Controller(UserService userService, InvoiceService invoiceService, GenerateInvoiceService generateInvoiceService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.generateInvoiceService = generateInvoiceService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request : {}", loginRequest);
        return userService.login(loginRequest);
    }

    @PostMapping(value = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createuser(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Create User Request : {}", createUserRequest);
        return userService.createUser(createUserRequest);
    }

    @GetMapping(value = "/userdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> userdetails() {
        log.info("Get UserDetails Request");
        return userService.userDetails();
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
