package com.jwt.security.configuration;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username;

        try{
            username = jwtService.extractUsername(jwt);
        }catch (JwtException e){
            response.sendError(400);
            return;
        }

        // SecurityContextHolder.getContext().getAuthentication() == null means that user is not authenticated
        if(username == null || SecurityContextHolder.getContext().getAuthentication() != null) return;

        // We are checking user from the DB
        // UserDetailsService bean is created in ApplicationConfig file where we implemented loadUserByUsername method
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(jwtService.isTokenValid(jwt, userDetails)){

            // Class needed by SpringSecurity to updated UserDetails info
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Method to save some additional details about the authentication request, such as the remote IP address and the session ID
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // I am passing request to filter chain
        filterChain.doFilter(request, response);
    }

}
