package com.app.restapimesen.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class UserMaterializedView {
    private String id;
    private String name;
    private String email;
    private String position;
    private String store_id;
}
