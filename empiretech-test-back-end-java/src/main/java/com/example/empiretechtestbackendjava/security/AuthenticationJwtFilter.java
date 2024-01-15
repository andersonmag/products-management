package com.example.empiretechtestbackendjava.security;

import com.example.empiretechtestbackendjava.config.DataSourceTenantConfig;
import com.example.empiretechtestbackendjava.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final DataSourceTenantConfig dataSourceTenantConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader(jwtService.getHeaderToken());

        if(token != null) {
            try {
                var authentication = jwtService.validAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException exception) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            var tenantIdHeader = "X-TenantyID";
            var tenant = request.getHeader(tenantIdHeader);

            if(tenant == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return;
            }
            dataSourceTenantConfig.setCurrentTenant(tenant);
            dataSourceTenantConfig.defineDataSourceByTenantUser();
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
