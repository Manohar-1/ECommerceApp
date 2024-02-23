package com.masai.ecommerce.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.masai.ecommerce.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtParser jwtParser; 
    
    @Autowired
    private UserService userService;

    public JwtTokenFilter(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (!isJwtRequest(header)) {
            // Not a JWT request, let the next filter handle it
            chain.doFilter(request, response);
            return;
        }

        String token = header.split(" ")[1];
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody(); // Parse and validate the token

            String username = claims.getSubject(); // Extract username from claims
            UserDetails userDetails = getUserDetailsByUsername(username); // Load user details from database

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = createAuthentication(claims, userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);
        } catch (SignatureException ex) {
            // Invalid signature
            logger.error("Invalid JWT signature");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (MalformedJwtException ex) {
            //  Invalid JWT format
            logger.error("Invalid JWT token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (ExpiredJwtException ex) {
            // Expired JWT token
            logger.error("Expired JWT token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (UnsupportedJwtException ex) {
            //  Unsupported JWT token
            logger.error("Unsupported JWT token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (IllegalArgumentException ex) {
            // Empty JWT claims
            logger.error("JWT claims string is empty.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private Authentication createAuthentication(Claims claims, UserDetails userDetails) {
    	return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private UserDetails getUserDetailsByUsername(String username) {
		return userService.loadUserByUsername(username);
	}

	private boolean isJwtRequest(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    
}

