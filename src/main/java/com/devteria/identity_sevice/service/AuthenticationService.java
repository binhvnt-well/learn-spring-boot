package com.devteria.identity_sevice.service;

import com.devteria.identity_sevice.dto.request.AuthenticationRequest;
import com.devteria.identity_sevice.exception.AppException;
import com.devteria.identity_sevice.exception.ErrorCode;
import com.devteria.identity_sevice.reponsitory.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // ..
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // ..
public class AuthenticationService {
    UserRepository userRepository;

    public boolean authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
    }
}
