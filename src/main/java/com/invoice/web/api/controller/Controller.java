package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.CostCenterRequest;
import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.request.DepartmentsRequest;
import com.invoice.web.api.dto.request.ExpenseTypeRequest;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.OtherDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1/invoice/")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    private final InvoiceService invoiceService;
    private final GenerateInvoiceService generateInvoiceService;
    private final OtherDetailsService otherDetailsService;

    public Controller(InvoiceService invoiceService,
                      GenerateInvoiceService generateInvoiceService, OtherDetailsService otherDetailsService) {
        this.invoiceService = invoiceService;
        this.generateInvoiceService = generateInvoiceService;
        this.otherDetailsService = otherDetailsService;
    }


    @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invoices() {
        log.info("Get Invoices request ");
        return invoiceService.invoices();
    }

    @GetMapping(value = "/invoiceDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getInvoiceDetails(@PathVariable String id) {
        log.info("Get Invoice details");
        return invoiceService.getInvoiceDetails(id);
    }


    @GetMapping(value = "/generateinvoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String generatePDFInvoice(@PathVariable String id) throws IOException {
        log.info("Generate Invoice PDF");
        return generateInvoiceService.createInvoice(id);
    }

    @GetMapping(value = "/otherdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOtherDetails() {
        log.info("Get Other details");
        return invoiceService.otherDetails();
    }

    @PostMapping(value = "/createinvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createinvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
        log.info("Create Invoice request: {}", createInvoiceRequest);
        return invoiceService.createInvoice(createInvoiceRequest);
    }

    @PostMapping(value = "/create-costcenter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCostCenter(@RequestBody CostCenterRequest costCenterRequest) {
        log.info("Cost Center request: {}", costCenterRequest);
        return otherDetailsService.createOrUpdateCostCenter(costCenterRequest);
    }

    @PostMapping(value = "/create-expensetype", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createExpenseType(@RequestBody ExpenseTypeRequest expenseTypeRequest) {
        log.info("Expense Type request: {}", expenseTypeRequest);
        return otherDetailsService.createOrUpdateExpenseType(expenseTypeRequest);
    }

        @PostMapping(value = "/create-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentsRequest departmentsRequest) {
        log.info("Department request: {}", departmentsRequest);
        return otherDetailsService.createOrUpdateDepartment(departmentsRequest);
    }

    @PostMapping(value = "/create-vendors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createVendor(@RequestBody VendorCreateRequest request) {
        log.info("Vendor Create request: {}", request);
        return otherDetailsService.createOrUpdateVendor(request);
    }

    @PostMapping(value = "/delete-costcenter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteCostCenter(@RequestParam String name) {
        log.info("Delete Center request: {}", name);
        return otherDetailsService.deleteCostCenter(name);
    }

    @PostMapping(value = "/delete-expensetype", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteExpenseType(@RequestParam String expenseName) {
        log.info("Delete Expense Type request: {}", expenseName);
        return otherDetailsService.deleteExpenseType(expenseName);
    }

    @PostMapping(value = "/delete-vendor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteVendor(@RequestBody String vendorId) {
        log.info("Delete Vendor request: {}", vendorId);
        return otherDetailsService.deleteVendor(vendorId);
    }

    @PostMapping(value = "/delete-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDepartment(@RequestParam String departmentName) {
        log.info("Delete Department request: {}", departmentName);
        return otherDetailsService.deleteDepartment(departmentName);
    }


    @GetMapping(value = "/costcenters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> costCenters() {
        return otherDetailsService.getCostCenters();
    }

    @GetMapping(value = "/expensetypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> expenseTypes() {
        return otherDetailsService.getExpenseTypes();
    }

    @GetMapping(value = "/departments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> departments() {
        return otherDetailsService.getDepartments();
    }

    @GetMapping(value = "/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> vendors() {
        return otherDetailsService.getVendors();
    }


}
