package org.hackaton.max2025.auth.service;


import org.hackaton.max2025.auth.model.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(String initData);
}
