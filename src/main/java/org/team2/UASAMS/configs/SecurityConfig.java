package org.team2.UASAMS.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.team2.UASAMS.CustomSuccessHandler;
import org.team2.UASAMS.Services.CustomUserDetailsService;

/**
 * Configures Spring Security for our application. 
 * 
 * This class defines authentication and authorization rules. It integrates our
 * CustomUserDetailsService and CustomSuccessHandler.
 * 
 * This class also defines the endpoints for login and logout. The password encoder is also
 * used to verify user credentials.
 *  
 * @author Joseph Corella
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomSuccessHandler customSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customSuccessHandler = customSuccessHandler;
    }

    // Configure HTTP security, authentication, and authorization rules
    // Links /login, /register for login, and /logout for logout
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/h2-console/**", "/register").permitAll()
                        .requestMatchers("/student/signup", "/faculty/signup").hasRole("GUEST")
                        .requestMatchers("/guest/**").hasRole("GUEST")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/faculty/**").hasAnyRole("ADMIN","REVIEWER", "PROVIDER", "STEWARD", "STAFF", "SUPPORT")
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .successHandler(customSuccessHandler)
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}