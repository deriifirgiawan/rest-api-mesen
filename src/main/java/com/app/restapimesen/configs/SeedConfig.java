package com.app.restapimesen.configs;

import com.app.restapimesen.entity.role.Role;
import com.app.restapimesen.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SeedConfig {
    private final RoleRepository repository;

    @Autowired
    public SeedConfig(RoleRepository repository) {
        this.repository = repository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initializeData() throws IOException {
        if (repository.count() == 0) {
            Role superAdmin = new Role();
            superAdmin.setName("SUPER_ADMIN");
            repository.save(superAdmin);


            Role admin = new Role();
            admin.setName("ADMIN");
            repository.save(admin);


            Role employee = new Role();
            employee.setName("EMPLOYEE");
            repository.save(employee);
        }
    }
}
