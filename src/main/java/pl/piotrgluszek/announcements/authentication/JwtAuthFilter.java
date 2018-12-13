package pl.piotrgluszek.announcements.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    TokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtToken(httpServletRequest);
        if (token != null && token != "" && jwtTokenManager.validateToken(token)) {

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    jwtTokenManager.getSubject(token),
                    null,
                    Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.contains("Bearer"))
            return authHeader.split(" ")[1];
        return null;
    }
}
