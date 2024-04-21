package com.app.restapimesen;

import com.app.restapimesen.services.materialized.MaterializedViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiMesenApplication {

    @Autowired
    private MaterializedViewService materializedViewService;

    public static void main(String[] args) {
        SpringApplication.run(RestApiMesenApplication.class, args);
    }
}
