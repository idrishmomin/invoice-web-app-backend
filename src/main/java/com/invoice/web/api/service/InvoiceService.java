package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.response.OtherDataResponse;
import com.invoice.web.api.dto.response.Response;
import com.invoice.web.infrastructure.utils.CommonUtils;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.*;
import com.invoice.web.persistence.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public InvoiceService(InvoiceRepository invoiceRepository, AccountsRepository accountsRepository, CostCenterRepository costCenterRepository, CurrenciesRepository currenciesRepository, DepartmentRepository departmentRepository, ExpenseCodesRepository expenseCodesRepository, VendorRepository vendorRepository, SubmitterRepository submitterRepository) {
        this.invoiceRepository = invoiceRepository;
        this.accountsRepository = accountsRepository;
        this.costCenterRepository = costCenterRepository;
        this.currenciesRepository = currenciesRepository;
        this.departmentRepository = departmentRepository;
        this.expenseCodesRepository = expenseCodesRepository;
        this.vendorRepository = vendorRepository;
        this.submitterRepository = submitterRepository;
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

        RequestParameterValidator.createInvoiceValidation(request);
        if (!request.getInvoiceNumber().isBlank()) {
            Invoice invoice = invoiceRepository.findByInvoiceNumber(request.getInvoiceNumber());
            if (null == invoice) {
                log.info("Invoice with invoiceNumber : {} does not exist", request.getInvoiceNumber());
                return Response.builder().response("Invoice with invoiceId does not exist ").build();
            } else {
                invoice.setUpdatedBy(request.getUpdatedBy());
                invoice.setInvoiceStatus(request.getInvoiceStatus());
                invoice.setInvoiceUpdatedDate(LocalDateTime.now());

                invoice.setTotal(request.getTotal());
                invoice.setAccountDetails(request.getAccountDetails());
                invoice.setVendorDetails(request.getVendorDetails());
                invoice.setSubmitter(request.getSubmitter());
                invoice.setItems(request.getItems());

                invoiceRepository.save(invoice);
                return Response.builder().response(invoice).build();
            }
        } else {
            String invoiceNumber = CommonUtils.generateInvoiceNumber();
            log.info("Invoice Generating with invoice Number : {}", invoiceNumber);
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(invoiceNumber);
            invoice.setCreatedBy(request.getCreatedBy());
            invoice.setInvoiceStatus(request.getInvoiceStatus());
            invoice.setInvoiceCreatedDate(LocalDateTime.now());

            invoice.setTotal(request.getTotal());
            invoice.setAccountDetails(request.getAccountDetails());
            invoice.setVendorDetails(request.getVendorDetails());
            invoice.setSubmitter(request.getSubmitter());
            invoice.setItems(request.getItems());

            invoiceRepository.save(invoice);
            return Response.builder().response(invoice).build();
        }
    }

    public Response<Object> otherDetails() {
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

        return Response.builder().response(otherDataResponse).build();
    }
}
