package es._7.todolist.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import es._7.todolist.services.JwtUtilService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtil;

    @Autowired
    public JwtAuthFilter(@Lazy JwtUtilService jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * This method is called for each request that comes to the server
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the token from the header
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String subID = null;

        // Check if the token is not null and starts with Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            System.out.println("jdsijifejwoigjiejgi"+token);

            try {
                // Extract roles from the token
                subID = jwtUtil.extractSubId(token);
                request.setAttribute("subID", subID);

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(subID)
                .password("")  // Empty password since it's not used
                .authorities(Collections.emptyList())  // No authorities needed
                .build();

                // Create authentication with UserDetails and no authorities
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,  // UserDetails as principal
                    null,         // No credentials
                    Collections.emptyList()  // Empty authorities list
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (Exception e) {
                // If the token is invalid or an exception occurs, clear the security context
                SecurityContextHolder.clearContext();
                e.printStackTrace();
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}