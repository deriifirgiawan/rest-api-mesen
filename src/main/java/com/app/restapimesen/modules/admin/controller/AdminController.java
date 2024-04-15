package com.app.restapimesen.modules.admin.controller;

import com.app.restapimesen.entity.user.Users;
import com.app.restapimesen.models.WebResponse;
import com.app.restapimesen.modules.admin.models.AddEmployeeRequest;
import com.app.restapimesen.modules.admin.models.AddStoreRequest;
import com.app.restapimesen.modules.admin.models.UpdateEmployeeRequest;
import com.app.restapimesen.modules.admin.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin")
public class AdminController {
    private final AdminService adminService;
    private final Validator validator;

    @PostMapping(path = "/add/store")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<String> addStore(
            @RequestBody AddStoreRequest request
    ) {
        validator.validate(request);
        var response = adminService.addStore(request);


        return WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .message("Success Add Store")
                .data(response)
                .build();
    }

    @PostMapping(path = "/add/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<String> addEmployee(
            @RequestBody AddEmployeeRequest request
    ) {
        validator.validate(request);
        var user = adminService.addEmployee(request);

        return WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .message("Success Add Employee")
                .data(user)
                .build();
    }

    @GetMapping(path = "/employee/all")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<List<Users>> getAllEmployee(
            @RequestParam(name = "store_id") String store_id
    ) {
        var users = adminService.getAllEmployee(store_id);


        return WebResponse.<List<Users>>builder()
                .status(HttpStatus.OK)
                .message("Success Get All Employee")
                .data(users)
                .build();
    }

    @PutMapping(path = "/update/employee")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<String> updateEmployee(
            @RequestBody UpdateEmployeeRequest request
    ){
        validator.validate(request);

        var response = adminService.updateEmployee(request);


        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Success Updated Employee")
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/delete/employee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<String> deleteOneEmployee(
            @NotNull @PathVariable(name = "id") String employeeId,
            @NotNull @RequestParam(name = "user_id") String userId
    ) {
        validator.validate(employeeId);
        validator.validate(userId);

        var response = adminService.deleteEmployeeById(employeeId, userId);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .message("Success Deleted Employee")
                .data(response)
                .build();
    }
}
