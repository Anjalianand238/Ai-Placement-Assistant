package com.placement.assistant.service;
import com.placement.assistant.dto.request.*;
import com.placement.assistant.dto.response.AuthResponse;
import com.placement.assistant.entity.*;
import com.placement.assistant.repository.*;
import com.placement.assistant.security.JwtUtil;
import com.placement.assistant.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthResponse register(RegisterRequest req){
        if(userRepository.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email already registered");
        User user=userRepository.save(User.builder().name(req.getName()).email(req.getEmail()).password(passwordEncoder.encode(req.getPassword())).role(User.Role.STUDENT).build());
        studentProfileRepository.save(StudentProfile.builder().user(user).build());
        UserDetails ud=userDetailsService.loadUserByUsername(user.getEmail());
        return AuthResponse.builder().token(jwtUtil.generateToken(ud)).type("Bearer").userId(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole().name()).build();
    }

    public AuthResponse login(LoginRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword()));
        User user=userRepository.findByEmail(req.getEmail()).orElseThrow();
        UserDetails ud=userDetailsService.loadUserByUsername(user.getEmail());
        return AuthResponse.builder().token(jwtUtil.generateToken(ud)).type("Bearer").userId(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole().name()).build();
    }
}
