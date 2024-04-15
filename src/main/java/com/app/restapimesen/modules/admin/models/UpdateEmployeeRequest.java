package com.app.restapimesen.modules.admin.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String position;

    @NotBlank
    private String user_id;


    @NotBlank
    private String store_id;
}
