package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CostCenterRequest;
import com.invoice.web.api.dto.request.DepartmentsRequest;
import com.invoice.web.api.dto.request.ExpenseTypeRequest;
import com.invoice.web.api.dto.request.VendorCreateRequest;
import com.invoice.web.api.dto.response.ApiResponse;
import com.invoice.web.infrastructure.utils.validation.RequestParameterValidator;
import com.invoice.web.persistence.model.CostCenter;
import com.invoice.web.persistence.model.Department;
import com.invoice.web.persistence.model.ExpenseCodes;
import com.invoice.web.persistence.model.Vendor;
import com.invoice.web.persistence.repositories.CostCenterRepository;
import com.invoice.web.persistence.repositories.DepartmentRepository;
import com.invoice.web.persistence.repositories.ExpenseCodesRepository;
import com.invoice.web.persistence.repositories.VendorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class OtherDetailsService {

    private final CostCenterRepository costCenterRepository;
    private final ExpenseCodesRepository expenseCodesRepository;

    private final DepartmentRepository departmentRepository;

    private final VendorRepository vendorRepository;

    public OtherDetailsService(CostCenterRepository costCenterRepository, ExpenseCodesRepository expenseCodesRepository, DepartmentRepository departmentRepository, VendorRepository vendorRepository) {
        this.costCenterRepository = costCenterRepository;
        this.expenseCodesRepository = expenseCodesRepository;
        this.departmentRepository = departmentRepository;
        this.vendorRepository = vendorRepository;
    }

    public ResponseEntity<Object> createOrUpdateCostCenter(CostCenterRequest costCenterRequest) {
        RequestParameterValidator.commonValidateRequest(costCenterRequest);
        String name = costCenterRequest.getName();
        CostCenter costCenter = costCenterRepository.findByName(name);

        if (costCenter != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Cost Center Already Exist");
        } else {
            log.info("Create New costCenter with name : {}", costCenterRequest.getName());
            costCenter = new CostCenter();
            costCenter.setName(costCenterRequest.getName());
            costCenter.setCode(costCenterRequest.getCode());
            costCenterRepository.save(costCenter);
        }

        if (null == costCenter) {

        } else {
            log.info("Update costCenter with name : {}", costCenterRequest.getName());
            costCenter.setCode(costCenterRequest.getCode());
            costCenterRepository.save(costCenter);
        }
        return ResponseEntity.status(HttpStatus.OK).body(costCenter);
    }

    public ResponseEntity<Object> createOrUpdateExpenseType(ExpenseTypeRequest expenseTypeRequest) {
        RequestParameterValidator.commonValidateRequest(expenseTypeRequest);
        ExpenseCodes expenseCodes = expenseCodesRepository.findByExpenseName(expenseTypeRequest.getExpenseName());
        if (null == expenseCodes) {
            log.info("Create New ExpenseType with name : {}", expenseTypeRequest.getExpenseName());
            expenseCodes = new ExpenseCodes();
            expenseCodes.setExpenseName(expenseTypeRequest.getExpenseName());
            expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
            expenseCodesRepository.save(expenseCodes);
        } else {
            log.info("Update ExpenseType with name : {}", expenseTypeRequest.getExpenseName());
            expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
            expenseCodesRepository.save(expenseCodes);
        }
        return ResponseEntity.status(HttpStatus.OK).body(expenseCodes);
    }

    public ResponseEntity<Object> createOrUpdateVendor(VendorCreateRequest request) {
        RequestParameterValidator.commonValidateRequest(request);
        Vendor vendor = vendorRepository.findByVendorId(request.getVendorId());
        if (null == vendor) {
            log.info("Create New Vendor with Id : {}", request.getVendorId());
            vendor = new Vendor();
            vendor.setVendorId(request.getVendorId());
            vendor.setVendorName(request.getVendorName());

            vendor.setBankDetails(request.getBankDetails());
            vendor.setAddress(request.getAddress());
            vendor.setPhoneNumber(request.getPhoneNumber());
            vendorRepository.save(vendor);
        } else {
            log.info("Update Vendor with name : {}", request.getVendorId());
            vendor.setVendorName(request.getVendorName());

            vendor.setBankDetails(request.getBankDetails());
            vendor.setAddress(request.getAddress());
            vendor.setPhoneNumber(request.getPhoneNumber());
            vendorRepository.save(vendor);
        }
        return ResponseEntity.status(HttpStatus.OK).body(vendor);
    }


    public ResponseEntity<Object> createOrUpdateDepartment(DepartmentsRequest departmentsRequest) {
        RequestParameterValidator.commonValidateRequest(departmentsRequest);
        Department department = departmentRepository.findByDepartmentName(departmentsRequest.getDepartmentName());
        if (null == department) {
            log.info("Create New Department with name : {}", departmentsRequest.getDepartmentName());
            department = new Department();
            department.setDepartmentName(departmentsRequest.getDepartmentName());
            department.setDepartmentManager(departmentsRequest.getDepartmentManager());
            departmentRepository.save(department);
        } else {
            log.info("Update Department with name : {}", department.getDepartmentName());
            department.setDepartmentManager(departmentsRequest.getDepartmentManager());
            departmentRepository.save(department);
        }
        return ResponseEntity.status(HttpStatus.OK).body(department);
    }

    public ResponseEntity<Object> getCostCenters() {
        List<CostCenter> costCenterList = costCenterRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(costCenterList);
    }

    public ResponseEntity<Object> getExpenseTypes() {
        List<ExpenseCodes> costCenterList = expenseCodesRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(costCenterList);
    }

    public ResponseEntity<Object> getDepartments() {
        List<Department> costCenterList = departmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(costCenterList);
    }


    public ResponseEntity<Object> getVendors() {
        List<Vendor> vendorList = vendorRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(vendorList);
    }

    public ResponseEntity<ApiResponse> deleteCostCenter(long id) {
        Optional<CostCenter> costCenter = costCenterRepository.findById(id);

        if (costCenter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Cost Center With Name Does not Exists"));
        }
        costCenterRepository.delete(costCenter.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Cost Center Deleted Successfully"));

    }

    public ResponseEntity<ApiResponse> deleteExpenseType(long id) {
        Optional<ExpenseCodes> expenseCodes = expenseCodesRepository.findById(id);

        if (expenseCodes.isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Expense Code With Name Does not Exists"));
        }
        expenseCodesRepository.delete(expenseCodes.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Expense Code Deleted Successfully"));

    }

    public ResponseEntity<ApiResponse> deleteVendor(int id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);

        if (vendor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Vendor With Id Does not Exists"));
        }
        vendorRepository.delete(vendor.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Vendor Deleted Successfully"));

    }

    public ResponseEntity<ApiResponse> deleteDepartment(long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Department Does not Exists"));
        }
        departmentRepository.delete(department.get());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Department Deleted Successfully"));
    }
}



