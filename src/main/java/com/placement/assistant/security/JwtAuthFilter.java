package com.placement.assistant.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
        String auth=req.getHeader("Authorization");
        String jwt=null,email=null;
        if(auth!=null&&auth.startsWith("Bearer ")){ jwt=auth.substring(7); try{email=jwtUtil.extractUsername(jwt);}catch(Exception ignored){} }
        if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails ud=userDetailsService.loadUserByUsername(email);
            if(jwtUtil.validateToken(jwt,ud)){
                var at=new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
                at.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(at);
            }
        }
        chain.doFilter(req,res);
    }
}
