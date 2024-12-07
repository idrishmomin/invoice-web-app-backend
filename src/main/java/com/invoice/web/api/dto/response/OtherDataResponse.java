package com.invoice.web.api.dto.response;

import com.invoice.web.persistence.model.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OtherDataResponse {

    List<Accounts> accountsList;
    List<CostCenter> costCenterList;
    List<Currencies> currenciesList;
    List<Department> departmentList;
    List<Submitter> submitterList;
    List<ExpenseCodes> expenseCodesList;
    List<Vendor> vendorList;
    List<String> paymentType;
    List<String> invoiceStatus;
    Map<String, List<ExpenseCodes>> expenseTypeByCategory;
    List<Category> categories;

}
