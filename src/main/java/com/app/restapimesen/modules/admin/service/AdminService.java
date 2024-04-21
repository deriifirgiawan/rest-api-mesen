package com.app.restapimesen.modules.admin.service;

import com.app.restapimesen.entity.user.UserMaterializedView;
import com.app.restapimesen.modules.admin.models.AddEmployeeRequest;
import com.app.restapimesen.modules.admin.models.AddStoreRequest;
import com.app.restapimesen.modules.admin.models.UpdateEmployeeRequest;

import java.util.List;

public interface AdminService {
    String addEmployee(AddEmployeeRequest request);
    String addStore(AddStoreRequest request);

    List<UserMaterializedView> getAllEmployee(String store_id);

    String updateEmployee(UpdateEmployeeRequest request);

    String deleteEmployeeById(String employee_id, String admin_id);
}
