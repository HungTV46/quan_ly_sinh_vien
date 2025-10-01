package com.example.QuanLySinhVien.config;

import com.example.QuanLySinhVien.dto.request.IntrospectRequest;
import com.example.QuanLySinhVien.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    private final AuthService authService;

    private NimbusJwtDecoder jwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authService.introspect(IntrospectRequest.builder()
                            .token(token)
                    .build());
            if (!response.getValid()){
                throw new JwtException("Invalid token");
            }
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(jwtDecoder)) {
            SecretKeySpec  secretKey = new SecretKeySpec(signerKey.getBytes(), "HmacSHA512");
            jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return jwtDecoder.decode(token);
    }
}
