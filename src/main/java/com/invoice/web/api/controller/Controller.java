package com.invoice.web.api.controller;

import com.invoice.web.api.dto.request.*;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.api.service.GenerateInvoiceService;
import com.invoice.web.api.service.InvoiceService;
import com.invoice.web.api.service.OtherDetailsService;
import com.invoice.web.api.service.UserService;
import com.invoice.web.api.service.loginservice.AuthenticationService;
import com.invoice.web.infrastructure.utils.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/webportal/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    private final UserService userService;
    private final InvoiceService invoiceService;
    private final GenerateInvoiceService generateInvoiceService;
    private final AuthenticationService authenticationService;
    private final OtherDetailsService otherDetailsService;
    private final JwtUtil jwtUtil;

    public Controller(UserService userService, InvoiceService invoiceService, GenerateInvoiceService generateInvoiceService, AuthenticationService authenticationService, OtherDetailsService otherDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.generateInvoiceService = generateInvoiceService;
        this.authenticationService = authenticationService;
        this.otherDetailsService = otherDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login Request : {}", loginRequest);

        if (loginRequest.getOtp() == null || loginRequest.getOtp().isEmpty()) {
            // First step: login with username and password
            String message = authenticationService.loginWithUsernamePassword(loginRequest.getEmail(), loginRequest.getPassword());
            return Response.builder().response(message).build();
        } else {
            // Second step: verify OTP
            boolean isOtpValid = authenticationService.verifyOtp(loginRequest.getEmail(), loginRequest.getOtp());
            if (isOtpValid) {
                // Generate JWT token upon successful OTP verification
                String jwtToken = jwtUtil.generateToken(loginRequest.getEmail());
                return Response.builder().response(jwtToken).build();
            } else {
                return Response.builder().response("Invalid OTP").build();
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        }
    }

    @PostMapping(value = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> createuser(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Create User Request : {}", createUserRequest);
        return userService.createUser(createUserRequest);
    }

    @GetMapping(value = "/userdetails/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Object> userdetails(@PathVariable String email) {
        log.info("Get UserDetails Request");
        return userService.userDetails(email);
    }

    @GetMapping(value = "/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> invoices() {
        log.info("Get Invoices request ");
        return invoiceService.invoices();
    }

    @GetMapping(value = "/invoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getInvoiceDetails(@PathVariable String id) {
        log.info("Get Invoice details");
        return invoiceService.getInvoiceDetails(id);
    }


    @GetMapping(value = "/generateinvoice/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] generatePDFInvoice(@PathVariable String id) throws IOException {
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


}
