package com.app.restapimesen.modules.auth.service;

import com.app.restapimesen.entity.role.Role;
import com.app.restapimesen.entity.user.Users;
import com.app.restapimesen.modules.auth.models.LoginRequest;
import com.app.restapimesen.modules.auth.models.LoginResponse;
import com.app.restapimesen.modules.auth.models.RegisterRequest;
import com.app.restapimesen.repository.RoleRepository;
import com.app.restapimesen.repository.UserRepository;
import com.app.restapimesen.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public String register(RegisterRequest request) {
        Optional<Users> users = userRepository.findUserByEmail(request.getEmail());

        if (users.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }

        Optional<Role> role = roleRepository.findRoleByName(request.getRole_name());

        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Role with name " + request.getRole_name() + " Not Found");
        }

        var user = Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role.orElseThrow())
                .position("Owner")
                .build();

        userRepository.save(user);

        return "Success Registration Please Login";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findUserByEmail(request.getEmail());

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Not Registered");
        }
        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.get().getPassword());

        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password");
        }

        String jwtToken = jwtService.generateToken(user.get());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(jwtToken);
        return loginResponse;
    }

    @Override
    public boolean checkEmailAvailable(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public String changePassword(String email, String password) {
        var user = userRepository.findUserByEmail(email).orElseThrow();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " Not Found");
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "Success Change Password";
    }
}
