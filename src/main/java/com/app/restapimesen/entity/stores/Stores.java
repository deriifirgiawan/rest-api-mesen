package com.app.restapimesen.entity.stores;

import com.app.restapimesen.entity.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "stores")
public class Stores {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_employees", nullable = false)
    private String totalEmployees;

    @Column(name = "open_time", nullable = false)
    private Timestamp openTime;

    @Column(name = "closed_time", nullable = false)
    private Timestamp closedTime;

    @Column(name = "number_of_tables", nullable = false)
    private Long numberOfTables;
}
