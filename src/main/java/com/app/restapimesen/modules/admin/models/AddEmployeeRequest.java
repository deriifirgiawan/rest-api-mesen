package com.app.restapimesen.modules.admin.models;

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
public class AddEmployeeRequest {
    @NotNull(message = "name not null")
    @NotBlank(message = "name not blank")
    private String name;

    @NotNull(message = "email not null")
    @NotBlank(message = "email not blank")
    private String email;

    @NotNull(message = "password not null")
    @NotBlank(message = "password not blank")
    private String password;


    @NotNull(message = "store_id not null")
    @NotBlank(message = "store_id not blank")
    private String store_id;

    @NotNull(message = "position not null")
    @NotBlank(message = "position not blank")
    private String position;
}
