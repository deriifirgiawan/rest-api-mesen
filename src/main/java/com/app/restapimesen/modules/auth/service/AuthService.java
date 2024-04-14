package com.app.restapimesen.modules.auth.service;

import com.app.restapimesen.modules.auth.models.LoginRequest;
import com.app.restapimesen.modules.auth.models.LoginResponse;
import com.app.restapimesen.modules.auth.models.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    boolean checkEmailAvailable(String email);

    String changePassword(String email, String password);
}
