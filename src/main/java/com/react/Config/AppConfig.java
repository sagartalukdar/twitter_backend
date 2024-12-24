package com.react.Config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
		https.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeHttpRequests(authorize->authorize.requestMatchers("/api/**").authenticated()
		.anyRequest().permitAll())
		.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
		.csrf().disable()
		.cors().configurationSource(corsConfigurationSource())
		.and().httpBasic().and().formLogin();
		return https.build();
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration ccf=new CorsConfiguration();
				ccf.setAllowedOrigins(Arrays.asList("http://localhost:3000/","https://twitter-frontend-zeta-lovat.vercel.app/"));
				ccf.setAllowedMethods(Collections.singletonList("*"));
				ccf.setAllowedHeaders(Collections.singletonList("*"));
				ccf.setExposedHeaders(Arrays.asList("Authorization"));
				ccf.setAllowCredentials(true);
				ccf.setMaxAge(36000000L);
				return ccf;
			}
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
