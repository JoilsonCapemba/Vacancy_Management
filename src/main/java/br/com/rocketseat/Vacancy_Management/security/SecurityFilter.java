package br.com.rocketseat.Vacancy_Management.security;

import br.com.rocketseat.Vacancy_Management.modules.company.CompanyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;



    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if(request.getRequestURI().startsWith("/companies")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            var token = this.recoverToken(request);
            if (token != null) {
                var tokenDecoded = this.tokenService.validateToken(token);

                if (tokenDecoded == null) response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                var roles = tokenDecoded.getClaim("roles").asList(Object.class);
                var grantes = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase())).toList();

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenDecoded.getSubject(), null, grantes);
                SecurityContextHolder.getContext().setAuthentication(auth);
                request.setAttribute("company_id", tokenDecoded.getSubject());
            }

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) return null;
        //return token.substring(7);
        return token.replace("Bearer ", "");
    }
}
