package com.devteria.identity_sevice.controller;

import com.devteria.identity_sevice.dto.request.ApiResponse;
import com.devteria.identity_sevice.dto.request.AuthenticationRequest;
import com.devteria.identity_sevice.dto.response.AuthenticationResponse;
import com.devteria.identity_sevice.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        boolean result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
            .data(AuthenticationResponse.builder()
                .authenticated(result).build())
            .build();
    }
}
