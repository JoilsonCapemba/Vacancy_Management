package br.com.rocketseat.Vacancy_Management.security;

import br.com.rocketseat.Vacancy_Management.modules.company.Company;
import br.com.rocketseat.Vacancy_Management.security.dto.TokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${secret.key}")
    private String secret;

    public TokenDTO generateToken(Company company) {
        var expiresAt = this.genExpirationDate();

        var token =  JWT.create()
                .withIssuer("Vacancy Manager")
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));

        return new TokenDTO(token, "Bearer", expiresAt);
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var tokenDecoded =  JWT.require(algorithm)
                    .withIssuer("Vacancy Manager")
                    .build()
                    .verify(token.toString());
            return tokenDecoded;
        }catch (JWTCreationException e){
            return null;
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
