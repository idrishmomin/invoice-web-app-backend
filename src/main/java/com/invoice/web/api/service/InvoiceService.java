package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.api.dto.response.OtherDataResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.infrastructure.Constants;
import com.invoice.web.infrastructure.utils.CommonUtils;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.*;
import com.invoice.web.persistence.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AccountsRepository accountsRepository;
    private final CostCenterRepository costCenterRepository;
    private final CurrenciesRepository currenciesRepository;
    private final DepartmentRepository departmentRepository;
    private final ExpenseCodesRepository expenseCodesRepository;
    private final VendorRepository vendorRepository;
    private final SubmitterRepository submitterRepository;

    private final CategoryRepository categoryRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, AccountsRepository accountsRepository, CostCenterRepository costCenterRepository, CurrenciesRepository currenciesRepository, DepartmentRepository departmentRepository, ExpenseCodesRepository expenseCodesRepository, VendorRepository vendorRepository, SubmitterRepository submitterRepository, CategoryRepository categoryRepository) {
        this.invoiceRepository = invoiceRepository;
        this.accountsRepository = accountsRepository;
        this.costCenterRepository = costCenterRepository;
        this.currenciesRepository = currenciesRepository;
        this.departmentRepository = departmentRepository;
        this.expenseCodesRepository = expenseCodesRepository;
        this.vendorRepository = vendorRepository;
        this.submitterRepository = submitterRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Object> invoices(Pageable pageable) {
        Page<Invoice> invoicePages = invoiceRepository.findAllInvoicesOrderedByCreatedDateDesc(pageable);
        log.info("Invoice List Size : {}", invoicePages.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(Constants.SUCCESS,invoicePages));
    }

    public ResponseEntity<Object> filteredInvoice(String invoiceNumber,String vendorName,String status,String createdBy,Pageable pageable) {
        Page<Invoice> invoicePages = invoiceRepository.findInvoiceByFilteredValues(invoiceNumber,vendorName,status,createdBy,pageable);
        log.info("Invoice List Size : {}", invoicePages.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(Constants.SUCCESS,invoicePages));
    }


    public ResponseEntity<Object> invoicesByUser(String createdBy, Pageable pageable) {
        Page<Invoice> invoicePages = invoiceRepository.findByCreatedByOrderByInvoiceCreatedDateDesc(createdBy,pageable);
        log.info("Invoice List Size : {}", invoicePages.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(Constants.SUCCESS,invoicePages));
    }

    public ResponseEntity<Object> getInvoiceDetails(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (null == invoice) {
            log.info("Invoice with invoiceNumber : {} does not exist", invoiceNumber);
        }
        return ResponseEntity.status(HttpStatus.OK).body(invoice);
    }


    public ResponseEntity<Object> createInvoice(CreateInvoiceRequest request) {

        RequestParameterValidator.createInvoiceValidation(request);
        if (!request.getInvoiceNumber().isBlank()) {
            Invoice invoice = invoiceRepository.findByInvoiceNumber(request.getInvoiceNumber());
            if (null == invoice) {
                log.info("Invoice with invoiceNumber : {} does not exist", request.getInvoiceNumber());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invoice with invoiceId does not exist");
            } else {
                log.info("Invoice Updated with invoice Number : {}", invoice.getInvoiceNumber());
                invoice.setUpdatedBy(request.getUpdatedBy());
                invoice.setInvoiceStatus(request.getInvoiceStatus());
                invoice.setInvoiceUpdatedDate(LocalDateTime.now());

                invoice.setTotal(request.getTotal());
                invoice.setAccountDetails(request.getAccountDetails());
                invoice.setVendorDetails(request.getVendorDetails());
                invoice.setSubmitter(request.getSubmitter());
                invoice.setItems(request.getItems());

                invoiceRepository.save(invoice);
                return ResponseEntity.status(HttpStatus.OK).body(invoice);
            }
        } else {
            String invoiceNumber = CommonUtils.generateInvoiceNumber();
            log.info("Invoice Generating with invoice Number : {}", invoiceNumber);
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(invoiceNumber);
            invoice.setCreatedBy(request.getCreatedBy());
            invoice.setInvoiceStatus(request.getInvoiceStatus());
            invoice.setInvoiceCreatedDate(LocalDateTime.now());
            invoice.setInvoiceUpdatedDate(LocalDateTime.now());

            invoice.setTotal(request.getTotal());
            invoice.setAccountDetails(request.getAccountDetails());
            invoice.setVendorDetails(request.getVendorDetails());
            invoice.setSubmitter(request.getSubmitter());
            invoice.setItems(request.getItems());

            invoiceRepository.save(invoice);
            return ResponseEntity.status(HttpStatus.OK).body(invoice);
        }
    }

    public ResponseEntity<Object> otherDetails() {
        List<String> paymentType = new ArrayList<>();
        paymentType.add("CASH");
        paymentType.add("CHEQUE");
        paymentType.add("BANK");

        List<String> invoiceStatus = new ArrayList<>();
        invoiceStatus.add("PENDING");
        invoiceStatus.add("SUBMITTED");
        invoiceStatus.add("REJECTED");

        List<Accounts> accountsList = accountsRepository.findAll();
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        List<Currencies> currenciesList = currenciesRepository.findAll();
        List<Department> departmentList = departmentRepository.findAll();
        List<ExpenseCodes> expenseCodesList = expenseCodesRepository.findAll();
        List<Vendor> vendorList = vendorRepository.findAll();
        List<Submitter> submitterList = submitterRepository.findAll();
        Map<String, List<ExpenseCodes>> filterdList = expenseCodesList.stream()
                .map(expenseCode -> new ExpenseCodes(expenseCode.getId(), expenseCode.getExpenseName(), expenseCode.getExpenseCode(), expenseCode.getCategory()))
                .collect(Collectors.groupingBy(ExpenseCodes::getCategory));

        List<Category> categoryList = categoryRepository.findAll();

        OtherDataResponse otherDataResponse = new OtherDataResponse();
        otherDataResponse.setAccountsList(accountsList);
        otherDataResponse.setCostCenterList(costCenterList);
        otherDataResponse.setCurrenciesList(currenciesList);
        otherDataResponse.setDepartmentList(departmentList);
        otherDataResponse.setExpenseCodesList(expenseCodesList);
        otherDataResponse.setVendorList(vendorList);
        otherDataResponse.setPaymentType(paymentType);
        otherDataResponse.setInvoiceStatus(invoiceStatus);
        otherDataResponse.setSubmitterList(submitterList);
        otherDataResponse.setExpenseTypeByCategory(filterdList);
        otherDataResponse.setCategories(categoryList);

        return ResponseEntity.status(HttpStatus.OK).body(otherDataResponse);
    }

    public ResponseEntity<ApiResponse> deleteInvoice(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber);

        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Invoice With Id Does not Exists"));
        }
        invoice.setDeleted(true);
        invoiceRepository.save(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Invoice Deleted Successfully"));

    }

    public ResponseEntity<Response<Object>> isVendorInvoiceRefAlreadyExists(String id) {
        List<Invoice> isExists =  invoiceRepository.findByVendorInvoiceRef(id);
        String message = null;
        if(!isExists.isEmpty()){
            message = "VendorInvoiceRef Already exists in invoiceNumber : ".concat(isExists.get(0).getInvoiceNumber());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(message));
    }
}
