package com.example.empiretechtestbackendjava.security;

import com.example.empiretechtestbackendjava.config.TenantContext;
import com.example.empiretechtestbackendjava.database.MultiTenantDataSource;
import com.example.empiretechtestbackendjava.service.JwtService;
import com.example.empiretechtestbackendjava.service.TenantServiceClient;
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
    private final TenantServiceClient tenantClient;
    private final MultiTenantDataSource tenantDataSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            setRequestTenant(request);

            var token = request.getHeader(jwtService.getHeaderToken());
            if (token != null) {
                try {
                    var authentication = jwtService.validAuthenticationToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException exception) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private void setRequestTenant(HttpServletRequest request) {
        final var domain = extractDomainFromHost(request.getServerName());
        if (domain.isEmpty()) {
            return;
        }

        var exists = tenantDataSource.existsTenantByDomain(domain);
        if (!exists) {
            var tenant = tenantClient.getTenant(domain);
            tenantDataSource.addTenant(tenant);
        }

        TenantContext.setTenant(domain);
    }

    private String extractDomainFromHost(String host) {
        return host.contains(".") ? host.split("\\.")[0] : "";
    }
}
