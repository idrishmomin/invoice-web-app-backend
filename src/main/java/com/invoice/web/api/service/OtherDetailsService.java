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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (null != costCenterRequest.getId()) {
            CostCenter costCenter = costCenterRepository.findByName(costCenterRequest.getName());
            if (null != costCenter) {
                if (costCenter.getId() != Long.valueOf(costCenterRequest.getId())) {
                    return ResponseEntity.status(HttpStatus.OK).body("Cost Center Already Exist");
                }
                costCenter.setCode(costCenterRequest.getCode());
                costCenter.setDescription(costCenterRequest.getDescription());
                costCenterRepository.save(costCenter);
                return ResponseEntity.status(HttpStatus.OK).body(costCenter);
            } else {
                log.info("Update costCenter with name : {}", costCenterRequest.getName());
                Optional<CostCenter> optCostCenter = costCenterRepository.findById(Long.valueOf(costCenterRequest.getId()));
                costCenter = optCostCenter.get();
                costCenter.setName(costCenterRequest.getName());
                costCenter.setCode(costCenterRequest.getCode());
                costCenter.setDescription(costCenterRequest.getDescription());
                costCenterRepository.save(costCenter);
                return ResponseEntity.status(HttpStatus.OK).body(costCenter);
            }
        }

        CostCenter costCenter = costCenterRepository.findByName(costCenterRequest.getName());

        if (costCenter != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Cost Center Already Exist");
        } else {
            log.info("Create new costCenter with name : {}", costCenterRequest.getName());
            costCenter = new CostCenter();
            costCenter.setName(costCenterRequest.getName());
            costCenter.setCode(costCenterRequest.getCode());
            costCenter.setDescription(costCenterRequest.getDescription());
            costCenterRepository.save(costCenter);
        }
        return ResponseEntity.status(HttpStatus.OK).body(costCenter);
    }

    public ResponseEntity<Object> createOrUpdateExpenseType(ExpenseTypeRequest expenseTypeRequest) {
        RequestParameterValidator.commonValidateRequest(expenseTypeRequest);

        if (null != expenseTypeRequest.getId()) {
            Optional<ExpenseCodes> optionalExpenseCodes = expenseCodesRepository.findByExpenseCode(expenseTypeRequest.getExpenseCode());
            ExpenseCodes expenseCodes;
            if (optionalExpenseCodes.isPresent()) {
                expenseCodes = optionalExpenseCodes.get();
                if (expenseCodes.getId() != Long.valueOf(expenseTypeRequest.getId())) {
                    return ResponseEntity.status(HttpStatus.OK).body("Expense Type Already Exist");
                }
                expenseCodes.setCategory(expenseTypeRequest.getCategory());
                expenseCodes.setExpenseName(expenseTypeRequest.getExpenseName());
                expenseCodesRepository.save(expenseCodes);
                return ResponseEntity.status(HttpStatus.OK).body(expenseCodes);
            } else {
                log.info("Update Expense Type with Expense Code : {}", expenseTypeRequest.getExpenseCode());
                Optional<ExpenseCodes> optCostCenter = expenseCodesRepository.findById(Long.valueOf(expenseTypeRequest.getId()));
                expenseCodes = optCostCenter.get();
                expenseCodes.setCategory(expenseTypeRequest.getCategory());
                expenseCodes.setExpenseName(expenseTypeRequest.getExpenseName());
                expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
                expenseCodesRepository.save(expenseCodes);
                return ResponseEntity.status(HttpStatus.OK).body(expenseCodes);
            }
        }

        Optional<ExpenseCodes> optionalExpenseCodes = expenseCodesRepository.findByExpenseCode(expenseTypeRequest.getExpenseCode());
        ExpenseCodes expenseCodes;
        if (optionalExpenseCodes.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Expense Type  Already Exist");
        } else {
            log.info("Create new Expense Type with Expense Code : {}", expenseTypeRequest.getExpenseCode());
            expenseCodes = new ExpenseCodes();
            expenseCodes.setCategory(expenseTypeRequest.getCategory());
            expenseCodes.setExpenseName(expenseTypeRequest.getExpenseName());
            expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
            expenseCodesRepository.save(expenseCodes);
        }
        return ResponseEntity.status(HttpStatus.OK).body(expenseCodes);
    }

    public ResponseEntity<Object> createOrUpdateVendor(VendorCreateRequest request) {
        RequestParameterValidator.commonValidateRequest(request);

        if (null != request.getId()) {
            Optional<Vendor> optionalVendor = vendorRepository.findByVendorId(request.getVendorId());
            Vendor vendor;
            if (optionalVendor.isPresent()) {
                vendor = optionalVendor.get();
                if (vendor.getId() != Long.valueOf(request.getId())) {
                    return ResponseEntity.status(HttpStatus.OK).body("Vendor Already Exist");
                }
                vendor.setVendorName(request.getVendorName());
                vendor.setBankDetails(request.getBankDetails());
                vendor.setAddress(request.getAddress());
                vendor.setPhoneNumber(request.getPhoneNumber());
                vendorRepository.save(vendor);
                return ResponseEntity.status(HttpStatus.OK).body(vendor);
            } else {
                log.info("Update Vendor with Vendor Id : {}", request.getVendorId());
                Optional<Vendor> optionalVendor1 = vendorRepository.findById(Long.valueOf(request.getId()));
                vendor = optionalVendor1.get();
                vendor.setVendorId(request.getVendorId());
                vendor.setVendorName(request.getVendorName());
                vendor.setBankDetails(request.getBankDetails());
                vendor.setAddress(request.getAddress());
                vendor.setPhoneNumber(request.getPhoneNumber());
                vendorRepository.save(vendor);
                return ResponseEntity.status(HttpStatus.OK).body(vendor);
            }
        }

        Optional<Vendor> optionalVendor = vendorRepository.findByVendorId(request.getVendorId());
        Vendor vendor;
        if (optionalVendor.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Vendor Already Exist");
        } else {
            log.info("Create new Vendor with Id : {}", request.getVendorId());
            vendor = new Vendor();
            vendor.setVendorId(request.getVendorId());
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

        if (null != departmentsRequest.getId()) {
            Optional<Department> optionalDepartment = departmentRepository.findByDepartmentName(departmentsRequest.getDepartmentName());
            Department department;
            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
                if (department.getId() != Long.valueOf(departmentsRequest.getId())) {
                    return ResponseEntity.status(HttpStatus.OK).body("Department Already Exist");
                }
                department.setSubmitter(departmentsRequest.getSubmitter());
                department.setDepartmentManager(departmentsRequest.getDepartmentManager());
                departmentRepository.save(department);
                return ResponseEntity.status(HttpStatus.OK).body(department);
            } else {
                log.info("Update Department with name : {}", departmentsRequest.getDepartmentName());
                Optional<Department> optCostCenter = departmentRepository.findById(Long.valueOf(departmentsRequest.getId()));
                department = optCostCenter.get();
                department.setDepartmentName(departmentsRequest.getDepartmentName());
                department.setSubmitter(departmentsRequest.getSubmitter());
                department.setDepartmentManager(departmentsRequest.getDepartmentManager());
                departmentRepository.save(department);
                return ResponseEntity.status(HttpStatus.OK).body(department);
            }
        }

        Optional<Department> optionalDepartment = departmentRepository.findByDepartmentName(departmentsRequest.getDepartmentName());

        Department department;
        if (optionalDepartment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("Department Already Exist");
        } else {
            log.info("Create new Department with name : {}", departmentsRequest.getDepartmentName());
            department = new Department();
            department.setSubmitter(departmentsRequest.getSubmitter());
            department.setDepartmentName(departmentsRequest.getDepartmentName());
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

    public ResponseEntity<Object> getExpenseTypesByCategory() {
        List<ExpenseCodes> expenseCodesList = expenseCodesRepository.findAll();
        Map<String, List<ExpenseCodes>> filterdList = expenseCodesList.stream()
                .map(expenseCode -> new ExpenseCodes(expenseCode.getId(), expenseCode.getExpenseName(), expenseCode.getExpenseCode(), expenseCode.getCategory()))
                .collect(Collectors.groupingBy(ExpenseCodes::getCategory));

        return ResponseEntity.status(HttpStatus.OK).body(filterdList);
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

    public ResponseEntity<ApiResponse> deleteVendor(long id) {
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



