package br.com.rocketseat.Vacancy_Management.security;

import br.com.rocketseat.Vacancy_Management.modules.candidate.Candidate;
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

@Service
public class TokenCandidateService {

    @Value("secret.keyCandidate")
    private String secret;

    public TokenDTO generateToken(Candidate candidate) {
        var expiresAt = this.genExpirationDate();

        var token =  JWT.create()
                .withIssuer("Vacancy Manager Candidate")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));

        TokenDTO tokenDTO = new TokenDTO(token, "Bearer", expiresAt);


        return tokenDTO;
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            var tokenDecoded = JWT.require(algorithm)
                    .withIssuer("Vacancy Manager Candidate")
                    .build()
                    .verify(token.toString());

            return tokenDecoded;


        }catch (JWTCreationException e){
            return null;
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
    }
}
