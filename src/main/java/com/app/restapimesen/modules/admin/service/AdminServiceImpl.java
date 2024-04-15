package com.app.restapimesen.modules.admin.service;

import com.app.restapimesen.entity.role.Role;
import com.app.restapimesen.entity.role.TypeRole;
import com.app.restapimesen.entity.stores.Stores;
import com.app.restapimesen.entity.user.Users;
import com.app.restapimesen.modules.admin.models.AddEmployeeRequest;
import com.app.restapimesen.modules.admin.models.AddStoreRequest;
import com.app.restapimesen.modules.admin.models.UpdateEmployeeRequest;
import com.app.restapimesen.repository.RoleRepository;
import com.app.restapimesen.repository.StoreRepository;
import com.app.restapimesen.repository.UserRepository;
import com.app.restapimesen.utils.FormatTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    @Override
    public String addEmployee(AddEmployeeRequest request) {
        Optional<Users> users = userRepository.findUserByEmail(request.getEmail());
        Optional<Stores> stores = storeRepository.findById(request.getStore_id());

        if (stores.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found");
        }

        if (users.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        Optional<Role> role = roleRepository.findRoleByName(String.valueOf(TypeRole.EMPLOYEE));

        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Role with name " + TypeRole.EMPLOYEE + " Not Found");
        }

        var user = Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role.orElseThrow())
                .stores(stores.get())
                .position(request.getPosition())
                .build();

        userRepository.save(user);

        return "Success Registration Employee";
    }

    @Override
    public String addStore(AddStoreRequest request) {
        FormatTime formatTime = new FormatTime();
        Optional<Users> users = userRepository.findById(request.getUser_id());
        Stores stores = new Stores();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with user_id: " + request.getUser_id() + " not found.");
        }

        stores.setName(request.getName());
        stores.setClosedTime(formatTime.formatTimeToTimestamp(request.getClosed_time()));
        stores.setOpenTime(formatTime.formatTimeToTimestamp(request.getOpen_time()));
        stores.setTotalEmployees(request.getTotal_employee());
        stores.setNumberOfTables(Long.valueOf(request.getNumber_of_tables()));

        storeRepository.save(stores);


        return "Success Configuration Store";
    }

    @Override
    public List<Users> getAllEmployee(String store_id) {
        return userRepository.findUsersByStoreId(store_id);
    }

    @Override
    public String updateEmployee(UpdateEmployeeRequest request) {
        var store = storeRepository.findById(request.getStore_id());
        var user = userRepository.findById(request.getUser_id());

        if (store.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Not Found");
        }

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        if (!Objects.equals(user.get().getStores().getId(), store.get().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not your Employee!!!");
        }

        user.get().setName(request.getName());
        user.get().setPosition(request.getPosition());

        userRepository.save(user.get());

        return "Success Edited Your Employee";
    }

    @Override
    public String deleteEmployeeById(String employee_id, String admin_id) {
        var users = userRepository.findById(admin_id);
        var employee = userRepository.findById(employee_id);

        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with id: " + employee_id + " Not Found");
        }

        if (!Objects.equals(users.get().getStores().getId(), employee.get().getStores().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is not your employee");
        }

        userRepository.delete(employee.get());

        return "Success Deleted One Employee";
    }
}
