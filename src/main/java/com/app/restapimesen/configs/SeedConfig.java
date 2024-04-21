package com.app.restapimesen.configs;

import com.app.restapimesen.entity.role.Role;
import com.app.restapimesen.modules.product.entity.Category;
import com.app.restapimesen.modules.product.repository.CategoryRepository;
import com.app.restapimesen.repository.RoleRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

@Component
public class SeedConfig {
    private final RoleRepository repository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public SeedConfig(RoleRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
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

        if (categoryRepository.count() == 0) {
            String pathFile = getClass().getResource("/static/categories.csv").getFile();
            try (Reader reader = new FileReader(pathFile);
                 CSVReader csvReader = new CSVReader(reader)) {

                String[] nextRecord;
                while ((nextRecord = csvReader.readNext()) != null) {
                    Category entity = new Category();
                    if (!Objects.equals(nextRecord[1], "name") && !Objects.equals(nextRecord[1], "")) {
                        entity.setName(nextRecord[1]);
                        categoryRepository.save(entity);
                    }
                }
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace(); // handle exception properly
            }
        }
    }
}
