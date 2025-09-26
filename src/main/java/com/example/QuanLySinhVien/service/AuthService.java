package com.example.QuanLySinhVien.service;

import com.example.QuanLySinhVien.dto.request.IntrospectRequest;
import com.example.QuanLySinhVien.dto.request.LoginRequest;
import com.example.QuanLySinhVien.dto.response.AuthResponse;
import com.example.QuanLySinhVien.dto.response.IntrospectResponse;
import com.example.QuanLySinhVien.entity.User;
import com.example.QuanLySinhVien.exception.AppException;
import com.example.QuanLySinhVien.exception.ErrorCode;
import com.example.QuanLySinhVien.repository.StudentRepository;
import com.example.QuanLySinhVien.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();

    }

    public AuthResponse authenticate(LoginRequest request) throws JOSEException {
        User user = userRepository.findByUsername(request.username());
        if(user ==null){
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND);
        }
        var auth = passwordEncoder.matches(request.password(), user.getPassword());

        if(!auth){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(request.username());

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToke(token)
                .build();
    }

    private String generateToken(String username) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("CustomClaim", "custom")
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jws = new JWSObject(header, payload);

        jws.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jws.serialize();
    }
}
