package com.app.restapimesen.modules.auth.models;

import com.app.restapimesen.models.RequiredProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "name is required")
    @NotNull(message = "name must not be null", groups = RequiredProperties.class)
    @Valid
    private String name;

    @NotBlank(message = "email is required")
    @NotNull(message = "email must not be null", groups = RequiredProperties.class)
    @Valid
    private String email;

    @NotBlank(message = "password is required")
    @NotNull(message = "password must not be null", groups = RequiredProperties.class)
    @Valid
    private String password;

    @NotBlank(message = "role_name is required")
    @NotNull(message = "role_name must not be null", groups = RequiredProperties.class)
    @Valid
    private String role_name;

    private String position;
}