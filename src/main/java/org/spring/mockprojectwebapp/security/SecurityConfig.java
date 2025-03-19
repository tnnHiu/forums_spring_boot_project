package org.spring.mockprojectwebapp.security;

import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/bootstrap-5.3.3-dist/**", "/bootstrap-icons-1.11.3/**").permitAll()
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/post/**").permitAll()
                        .requestMatchers("/post/*/load-more-comments").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(
                                (request, response, authentication) -> {
                                    String email = authentication.getName();
                                    User user = authService.findByEmail(email);
                                    request.getSession().setAttribute("userId", user.getUserId());
                                    request.getSession().setAttribute("userEmail", user.getEmail());
                                    request.getSession().setAttribute("userName", user.getUsername());
                                    var authorities = authentication.getAuthorities();
                                    for (var authority : authorities) {
                                        if (authority.getAuthority().equals("ROLE_ADMIN")) {
                                            response.sendRedirect("/admin");
                                            return;
                                        } else if (authority.getAuthority().equals("ROLE_USER")) {
                                            response.sendRedirect("/");
                                            return;
                                        }
                                    }
                                    response.sendRedirect("/");
                                })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

