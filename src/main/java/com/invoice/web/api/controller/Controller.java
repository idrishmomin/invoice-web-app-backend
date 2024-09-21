package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/webportal/v1")
public class Controller {

    private final UserService userService;

    private final InvoiceService invoiceService;

    public Controller(UserService userService, InvoiceService invoiceService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request : {}", loginRequest);
        return userService.login(loginRequest);
    }

    @PostMapping(value = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createuser(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        log.info("Create User Request : {}", createInvoiceRequest);
        return userService.createUser(createInvoiceRequest);
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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/otherdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> getOtherDetails() {
        log.info("Get Other details");
        return invoiceService.otherDetails();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/createinvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createinvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        log.info("Create Invoice request: {}", createInvoiceRequest);
        return invoiceService.createInvoice(createInvoiceRequest);
    }

}
