package com.denkishop.mySecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter authFilter;
    
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;

    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("test01")
//                .password(encoder.encode("test01"))
//                .roles("ROLE_ADMIN")
//                .build();
//        UserDetails user = User.withUsername("test02")
//                .password(encoder.encode("test02"))
//                .roles("ROLE_USER","ROLE_SALES")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
        return new UserInfoUserDetailsService();
    }
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		try {
			http.cors().and().csrf().disable().exceptionHandling()
			        .authenticationEntryPoint(authEntryPoint).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeHttpRequests()
					.requestMatchers("/").permitAll()
					.requestMatchers("/users/login").permitAll()
					.requestMatchers("/users/new").permitAll()
					.requestMatchers("/products/**").authenticated()
					.requestMatchers("/sales/**").authenticated()
					.requestMatchers("/stocks/**").authenticated()
					.requestMatchers("/audit/**").authenticated()
					.requestMatchers("/actuator/**").authenticated()
					.anyRequest().authenticated().and().httpBasic();
			http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

			return http.build();

		} catch (IllegalStateException e) {
			throw new IllegalStateException(String.format("Token cannot be trusted"));
		}
	}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
