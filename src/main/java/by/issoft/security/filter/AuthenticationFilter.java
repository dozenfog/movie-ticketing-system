package by.issoft.security.filter;

import by.issoft.security.jwt.JWTProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
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
    }
}
