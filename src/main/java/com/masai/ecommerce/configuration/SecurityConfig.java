package com.masai.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Configuration
public class SecurityConfig {
	
	@Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() 
            .authorizeRequests()
                .requestMatchers("/login", "/api/register").permitAll() 
                .requestMatchers("/api/**").authenticated() 
                .and()
            .addFilterBefore(new JwtTokenFilter(jwtParser()), UsernamePasswordAuthenticationFilter.class) // Add JWT filter before default login filter
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
