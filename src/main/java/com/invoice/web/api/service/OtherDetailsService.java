package com.invoice.web.api.service;

import com.invoice.web.api.dto.request.CostCenterRequest;
import com.invoice.web.api.dto.request.DepartmentsRequest;
import com.invoice.web.api.dto.request.ExpenseTypeRequest;
import com.invoice.web.api.dto.request.VendorCreateRequest;
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
        List<CostCenter> costCenterList = costCenterRepository.findByName(costCenterRequest.getName());

        if(costCenterList.size() > 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cost Center With Name Already Exists");
        }

        CostCenter costCenter = null;
        if(!costCenterList.isEmpty()) {
            costCenter = costCenterList.get(0);
        }

        if (null == costCenter) {
            log.info("Create New costCenter with name : {}",costCenterRequest.getName());
            costCenter = new CostCenter();
            costCenter.setName(costCenterRequest.getName());
            costCenter.setCode(costCenterRequest.getCode());
            costCenterRepository.save(costCenter);
        } else {
            log.info("Update costCenter with name : {}",costCenterRequest.getName());
            costCenter.setCode(costCenterRequest.getCode());
            costCenterRepository.save(costCenter);
        }
        return ResponseEntity.status(HttpStatus.OK).body(costCenter);
    }

    public ResponseEntity<Object> createOrUpdateExpenseType(ExpenseTypeRequest expenseTypeRequest) {
        RequestParameterValidator.commonValidateRequest(expenseTypeRequest);
        ExpenseCodes expenseCodes = expenseCodesRepository.findByExpenseName(expenseTypeRequest.getExpenseName());
        if (null == expenseCodes) {
            log.info("Create New ExpenseType with name : {}",expenseTypeRequest.getExpenseName());
            expenseCodes = new ExpenseCodes();
            expenseCodes.setExpenseName(expenseTypeRequest.getExpenseName());
            expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
            expenseCodesRepository.save(expenseCodes);
        } else {
            log.info("Update ExpenseType with name : {}",expenseTypeRequest.getExpenseName());
            expenseCodes.setExpenseCode(expenseTypeRequest.getExpenseCode());
            expenseCodesRepository.save(expenseCodes);
        }
        return ResponseEntity.status(HttpStatus.OK).body(expenseCodes);
    }

    public ResponseEntity<Object> createOrUpdateVendor(VendorCreateRequest request) {
        RequestParameterValidator.commonValidateRequest(request);
        Vendor vendor = vendorRepository.findByVendorId(request.getVendorId());
        if (null == vendor) {
            log.info("Create New Vendor with Id : {}",request.getVendorId());
            vendor = new Vendor();
            vendor.setVendorId(request.getVendorId());
            vendor.setVendorName(request.getVendorName());

            vendor.setBankDetails(request.getBankDetails());
            vendor.setAddress(request.getAddress());
            vendor.setPhoneNumber(request.getPhoneNumber());
            vendorRepository.save(vendor);
        } else {
            log.info("Update Vendor with name : {}",request.getVendorId());
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
            log.info("Create New Department with name : {}",departmentsRequest.getDepartmentName());
            department = new Department();
            department.setDepartmentName(departmentsRequest.getDepartmentName());
            department.setDepartmentManager(departmentsRequest.getDepartmentManager());
            departmentRepository.save(department);
        } else {
            log.info("Update Department with name : {}",department.getDepartmentName());
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

    public ResponseEntity<Object> deleteCostCenter(String name) {
        CostCenter costCenter = costCenterRepository.findCostCenter(name);

        if(null == costCenter){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cost Center With Name Does not Exists");
        }
        costCenterRepository.delete(costCenter);
        return ResponseEntity.status(HttpStatus.OK).body("Cost Center Deleted Successfully");

    }

    public ResponseEntity<Object> deleteExpenseType(String expenseTypeName) {
        ExpenseCodes expenseCodes = expenseCodesRepository.findByExpenseName(expenseTypeName);

        if(null == expenseCodes){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Expense Code With Name Does not Exists");
        }
        expenseCodesRepository.delete(expenseCodes);
        return ResponseEntity.status(HttpStatus.OK).body("Expense Code Deleted Successfully");

    }

    public ResponseEntity<Object> deleteVendor(String vendorId) {
        Vendor vendor = vendorRepository.findByVendorId(vendorId);

        if(null == vendor){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vendor With Id Does not Exists");
        }
        vendorRepository.delete(vendor);
        return ResponseEntity.status(HttpStatus.OK).body("Vendor Deleted Successfully");

    }

    public ResponseEntity<Object> deleteDepartment(String departmentName) {
        Department department = departmentRepository.findByDepartmentName(departmentName);

        if(null == department){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department Does not Exists");
        }
        departmentRepository.delete(department);
        return ResponseEntity.status(HttpStatus.OK).body("Department Deleted Successfully");
    }
}



