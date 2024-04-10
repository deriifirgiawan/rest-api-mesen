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
public class AddStoreRequest {
    @NotNull(message = "name not null")
    @NotBlank(message = "name not blank")
    private String name;

    @NotNull(message = "total_employee not null")
    @NotBlank(message = "total_employee not blank")
    private String total_employee;

    @NotNull(message = "user_id not null")
    @NotBlank(message = "user_id not blank")
    private String user_id;

    @NotNull(message = "number_of_tables not null")
    @NotBlank(message = "number_of_tables not blank")
    private String number_of_tables;

    @NotNull(message = "open_time not null")
    @NotBlank(message = "open_time not blank")
    private String open_time;

    @NotNull(message = "closed_time not null")
    @NotBlank(message = "closed_time not blank")
    private String closed_time;
}
