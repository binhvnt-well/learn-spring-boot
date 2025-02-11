package com.devteria.identity_sevice.service;

import com.devteria.identity_sevice.dto.request.AuthenticationRequest;
import com.devteria.identity_sevice.dto.request.IntrospectRequest;
import com.devteria.identity_sevice.dto.response.AuthenticationResponse;
import com.devteria.identity_sevice.dto.response.IntrospectResponse;
import com.devteria.identity_sevice.exception.AppException;
import com.devteria.identity_sevice.exception.ErrorCode;
import com.devteria.identity_sevice.reponsitory.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor // ..
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // ..
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    // gen SIGNER_KEY trên web https://generate-random.org/encryption-key-generator?count=1&bytes=32&cipher=aes-256-cbc&string=&password=
//    protected static final String SIGNER_KEY =
//        "y+isKQXASaVT7fdaHZjyuxyG+X4MdglsloeY5xcWknh8U5qSyIdRmQMLVr6acxxc";

    // Add key public vào application.yaml file.
    @Value("jwt.signerKey")
    private String signerKey;


    public IntrospectResponse introspect(IntrospectRequest request)
        throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
            .valid(verified && expiryTime.after(new Date()))
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(),
            user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(authenticationRequest.getUsername());

        return AuthenticationResponse.builder()
            .token(token)
            .authenticated(true)
            .build();
    }

    // Ôn lại ở youtube: https://www.youtube.com/watch?v=1XC5WPQkXek&list=PL2xsxmVse9IaxzE8Mght4CFltGOqcG6FC&index=9&ab_channel=Devteria
    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(username)
            .issuer("bagwell.com")
            .issueTime(new Date())
            .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
            .claim("customClaim", "Custom")
            .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error("cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
