package com.app.restapimesen.modules.auth.controller;

import com.app.restapimesen.models.WebResponse;
import com.app.restapimesen.modules.auth.models.LoginRequest;
import com.app.restapimesen.modules.auth.models.LoginResponse;
import com.app.restapimesen.modules.auth.models.RegisterRequest;
import com.app.restapimesen.modules.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Authentication", description = "Authentication End Point for Registered and Login Users like ADMIN, EMPLOYEE")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final Validator validator;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public WebResponse<String> register(
            @RequestBody RegisterRequest request
    ) {
        validator.validate(request);
        var user = service.register(request);

        return WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .message("Success Registrations")
                .data(user)
                .build();
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        validator.validate(request);

        var response = service.login(request);

        return WebResponse.<LoginResponse>builder()
                .status(HttpStatus.CREATED)
                .message("Success Login")
                .data(response)
                .build();
    }

    @PostMapping(path = "/change-password")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<String> changePassword(
            @RequestBody LoginRequest request
    ) {
        validator.validate(request);

        var response = service.changePassword(request.getEmail(), request.getPassword());

        return WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .message("Success Change Password")
                .data(response)
                .build();
    }

    @PostMapping(path = "/check-email")
    @ResponseStatus(HttpStatus.OK)
    public WebResponse<Boolean> checkEmailAvailable(
            @RequestBody @NotBlank String email
    ) {
        validator.validate(email);

        var response = service.checkEmailAvailable(email);

        if (response) {
            return WebResponse.<Boolean>builder()
                    .status(HttpStatus.OK)
                    .message("Email Available")
                    .data(true)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " Not Found");
        }
    }
}
