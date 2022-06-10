package by.issoft.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static by.issoft.security.Constants.AUTHORITIES_KEY;
import static by.issoft.security.Constants.PREFIX;
import static by.issoft.security.Constants.SECRET_KEY;

@Component
@Slf4j
public class JWTProvider {
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public String generateToken(UserDetails user, String requestURI) {
        return PREFIX + JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                .withIssuer(requestURI)
                .withClaim(AUTHORITIES_KEY, user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(ALGORITHM);
    }

    public DecodedJWT decodeToken(final String accessToken) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(accessToken);
    }
}
