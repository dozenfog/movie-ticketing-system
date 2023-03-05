package by.issoft.security.filter;

import by.issoft.security.jwt.JWTProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.issoft.security.Constants.AUTHORITIES_KEY;
import static by.issoft.security.Constants.AUTHORIZATION_HEADER;
import static by.issoft.security.Constants.LOGIN_PATH;
import static by.issoft.security.Constants.PREFIX;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getServletPath().equals(LOGIN_PATH)) {
            /*if (request.getCookies() != null) {
                processAuthorizationHeader(Arrays.stream(request.getCookies())
                        .filter(cookie -> "auth".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findAny());
            }*/
            processAuthorizationHeader(Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER)));
        }
        filterChain.doFilter(request, response);
    }

    private void processAuthorizationHeader(Optional<String> authorizationHeader) {
        if (authorizationHeader.isPresent() && authorizationHeader.get().startsWith(PREFIX)) {
            String accessToken = authorizationHeader.get().replace(PREFIX, "");
            DecodedJWT decodedJWT = new JWTProvider().decodeToken(accessToken);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, getAuthorities(decodedJWT));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private List<GrantedAuthority> getAuthorities(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(AUTHORITIES_KEY)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
