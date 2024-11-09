package com.invoice.web.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.web.api.dto.request.*;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.OtherDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1/invoice/")
public class Controller {

    private final InvoiceService invoiceService;
    private final GenerateInvoiceService generateInvoiceService;
    private final OtherDetailsService otherDetailsService;

    ObjectMapper mapper = new ObjectMapper();
    public Controller(InvoiceService invoiceService,
                      GenerateInvoiceService generateInvoiceService, OtherDetailsService otherDetailsService) {
        this.invoiceService = invoiceService;
        this.generateInvoiceService = generateInvoiceService;
        this.otherDetailsService = otherDetailsService;
    }


    @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invoices(Pageable pageable) {
        log.info("Get Invoices request ");
        return invoiceService.invoices(pageable);
    }

    @GetMapping(value = "/filtered-invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> filteredInvoices(@RequestParam(required = false) String invoiceNumber,
                                                   @RequestParam(required = false) String vendorName,
                                                   @RequestParam(required = false) String status,
                                                   Pageable pageable) {
//        log.info("Get Filtered-Invoice Request with filters - InvoiceNumber: {}, VendorName: {}, Status: {}",
//                invoiceNumber, vendorName, status);
        return invoiceService.filteredInvoice(invoiceNumber,vendorName,status,pageable);
    }

    @GetMapping(value = "/invoices-by-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invoicesByUsers(@RequestParam String createdBy,Pageable pageable) {
        log.info("Get Invoices for user : {}",createdBy);
        return invoiceService.invoicesByUser(createdBy,pageable);
    }

    @GetMapping(value = "/invoiceDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getInvoiceDetails(@PathVariable String id) {
        log.info("Get Invoice details");
        return invoiceService.getInvoiceDetails(id);
    }


    @GetMapping(value = "/is-vendor-invoiceRef-exists/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Object>> isVendorInvoiceRefAlreadyExist(@PathVariable String id) {
        return invoiceService.isVendorInvoiceRefAlreadyExists(id);
    }

    @GetMapping(value = "/generateinvoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String generatePDFInvoice(@PathVariable String id) throws IOException {
        log.info("Generate Invoice PDF for Id : {}",id);
        return generateInvoiceService.createInvoice(id);
    }

    @DeleteMapping(value = "/delete-invoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteInvoice(@RequestParam String id) {
        log.info("Delete Invoice request: {}", id);
        return invoiceService.deleteInvoice(id);
    }

    @GetMapping(value = "/otherdetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOtherDetails() {
        log.info("Get Other details");
        return invoiceService.otherDetails();
    }

    @PostMapping(value = "/createinvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createinvoice(@RequestBody CreateInvoiceRequest createInvoiceRequest) throws JsonProcessingException {
        log.info("Create Invoice request: {}", mapper.writeValueAsString(createInvoiceRequest));
        return invoiceService.createInvoice(createInvoiceRequest);
    }

    @PostMapping(value = "/create-costcenter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCostCenter(@RequestBody CostCenterRequest costCenterRequest) throws JsonProcessingException {
        log.info("Cost Center request: {}", mapper.writeValueAsString(costCenterRequest));
        return otherDetailsService.createOrUpdateCostCenter(costCenterRequest);
    }

    @PostMapping(value = "/create-expensetype", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createExpenseType(@RequestBody ExpenseTypeRequest expenseTypeRequest) throws JsonProcessingException {
        log.info("Expense Type request: {}", mapper.writeValueAsString(expenseTypeRequest));
        return otherDetailsService.createOrUpdateExpenseType(expenseTypeRequest);
    }

    @PostMapping(value = "/create-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDepartment(@RequestBody DepartmentsRequest departmentsRequest) throws JsonProcessingException {
        log.info("Department request: {}", mapper.writeValueAsString(departmentsRequest));
        return otherDetailsService.createOrUpdateDepartment(departmentsRequest);
    }

    @PostMapping(value = "/create-vendors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createVendor(@RequestBody VendorCreateRequest request) throws JsonProcessingException {
        log.info("Vendor Create request: {}", mapper.writeValueAsString(request));
        return otherDetailsService.createOrUpdateVendor(request);
    }

    @DeleteMapping(value = "/delete-costcenter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteCostCenter(@RequestParam long id) {
        log.info("Delete Center request: {}", id);
        return otherDetailsService.deleteCostCenter(id);
    }

    @DeleteMapping(value = "/delete-expensetype", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteExpenseType(@RequestParam long id) {
        log.info("Delete Expense Type request: {}", id);
        return otherDetailsService.deleteExpenseType(id);
    }

    @DeleteMapping(value = "/delete-vendor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteVendor(@RequestParam long id) {
        log.info("Delete Vendor request: {}", id);
        return otherDetailsService.deleteVendor(id);
    }

    @DeleteMapping(value = "/delete-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteDepartment(@RequestParam long id) {
        log.info("Delete Department request: {}", id);
        return otherDetailsService.deleteDepartment(id);
    }

    @GetMapping(value = "/costcenters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> costCenters() {
        return otherDetailsService.getCostCenters();
    }

    @GetMapping(value = "/expensetypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> expenseTypes() {
        return otherDetailsService.getExpenseTypes();
    }

    @GetMapping(value = "/expensetypes-by-category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> expenseTypesByCategory() {
        return otherDetailsService.getExpenseTypesByCategory();
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
