package com.invoice.web.api.dto.response;

import com.invoice.web.persistence.model.*;
import lombok.*;

import java.util.List;

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
    List<ExpenseCodes> expenseCodesList;
    List<Vendor> vendorList;

}
