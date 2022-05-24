package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SpringSecurity {

	@Value("${jwks_uri}")
	String jwksUri;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET).hasAuthority("User")
		.anyRequest()
		.authenticated()
		.and()
		.cors().disable()
		.oauth2ResourceServer()
		.jwt()
		//Either we can use below line or we can use properties file 
		// If we use properties file then we CANT use custom validations 
		.decoder(jwtDecoder())
		.jwtAuthenticationConverter(getAuthenticationConverter());

		return http.build();
	}
	
	// We can use jwtDecoder to have custom validations
	// Here we have custom validation for Audience i.e. clientIds
	JwtDecoder jwtDecoder() {
		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(this.jwksUri).build();

		List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
		validators.add(new JwtTimestampValidator());
		validators.add(new AudienceValidator());

		jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(validators));
		return jwtDecoder;
	}

	// This is for authorization.
	// This is used to convert our claims(i.e. custom attributes or groups of cognito) into authority 
	JwtAuthenticationConverter getAuthenticationConverter() {
		JwtAuthenticationConverter auth = new JwtAuthenticationConverter();
		auth.setJwtGrantedAuthoritiesConverter(jwt -> { 
			return Arrays.asList(new SimpleGrantedAuthority("User"));
		});
		return auth;
	}
}
