package org.team2.UASAMS;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Determine role
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_GUEST")) {
            response.sendRedirect("/guest/home");
        } 
        else if (role.equals("ROLE_STUDENT")) {
            response.sendRedirect("/student/home");
        } 
        else if (role.equals("ROLE_ADMIN")) {
            response.sendRedirect("/faculty/home");
        } 
        else if (role.equals("ROLE_REVIEWER")) {
            response.sendRedirect("/faculty/home");
        } 
        else if (role.equals("ROLE_PROVIDER")) {
            response.sendRedirect("/faculty/home");
        } 
        else if (role.equals("ROLE_STEWARD")) {
            response.sendRedirect("/faculty/home");
        } 
        else if (role.equals("ROLE_STAFF")) {
            response.sendRedirect("/faculty/home");
        } 
        else if (role.equals("ROLE_SUPPORT")) {
            response.sendRedirect("/faculty/home");
        } 
        else {
            SecurityContextHolder.clearContext();
            response.sendRedirect("/login?error=role");
        }
    }
}
