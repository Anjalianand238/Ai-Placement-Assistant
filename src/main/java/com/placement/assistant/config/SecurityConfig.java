package com.placement.assistant.config;
import com.placement.assistant.security.JwtAuthFilter;
import com.placement.assistant.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @EnableMethodSecurity @RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(a->a
                .requestMatchers("/api/auth/**","/swagger-ui/**","/v3/api-docs/**","/uploads/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean public AuthenticationProvider authenticationProvider(){
        var p=new DaoAuthenticationProvider(); p.setUserDetailsService(userDetailsService); p.setPasswordEncoder(passwordEncoder()); return p;
    }
    @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception{return c.getAuthenticationManager();}
    @Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
