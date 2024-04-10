package com.app.restapimesen.modules.admin.service;

import com.app.restapimesen.entity.user.Users;
import com.app.restapimesen.modules.admin.models.AddEmployeeRequest;
import com.app.restapimesen.modules.admin.models.AddStoreRequest;

import java.util.List;

public interface AdminService {
    String addEmployee(AddEmployeeRequest request);
    String addStore(AddStoreRequest request);

    List<Users> getAllEmployee(String store_id);
}
