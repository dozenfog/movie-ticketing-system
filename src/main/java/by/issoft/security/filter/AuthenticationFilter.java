package by.issoft.security.filter;

import by.issoft.security.jwt.JWTProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.issoft.security.Constants.AUTHORIZATION_HEADER;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String requestURI = request.getRequestURI();

        response.addHeader(AUTHORIZATION_HEADER, new JWTProvider().generateToken(user, requestURI));
        /*Cookie jwtTokenCookie = new Cookie("auth", new JWTProvider().generateToken(user, requestURI));
        jwtTokenCookie.setSecure(true);
        jwtTokenCookie.setHttpOnly(false);
        jwtTokenCookie.setPath("/");
        response.addCookie(jwtTokenCookie);*/

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8000");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }
}
