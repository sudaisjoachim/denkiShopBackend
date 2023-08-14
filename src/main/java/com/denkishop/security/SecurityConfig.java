package com.denkishop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private JwtAuthEntryPoint authEntryPoint;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		try {
			http.cors().and().csrf().disable().exceptionHandling()
			        .authenticationEntryPoint(authEntryPoint).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeHttpRequests()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/api-docs/**").permitAll()
					.requestMatchers("/").permitAll()  
					.requestMatchers("/login/**").permitAll()
					.requestMatchers("/logout/**").permitAll()
					.requestMatchers("/activate/**").permitAll()
					.requestMatchers("/users/email/**").permitAll()
					.requestMatchers("/users/password/reset/**").permitAll()
					.requestMatchers("/products/**").hasRole("ADMIN")
					.requestMatchers("/stocks/**").hasRole("ADMIN")
					.requestMatchers("/sales/**").hasRole("ADMIN")
					.anyRequest().authenticated().and().httpBasic();
			http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

			return http.build();

		} catch (IllegalStateException e) {
			throw new IllegalStateException(String.format("Token cannot be trusted"));
		}
	}	
}
