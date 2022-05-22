package com.example.demo;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SpringSecurity {

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
		//.decoder(jwtDecoder())
		.jwtAuthenticationConverter(getAuthenticationConverter());

		return http.build();
	}

//	JwtDecoder jwtDecoder() {
//		NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("jwks_uri").build();
//		return jwtDecoder;
//	}
	
	//This is used to convert our claims(i.e. custom attributes or groups of cognito) into authority 
	// for Authorization
	JwtAuthenticationConverter getAuthenticationConverter() {
		JwtAuthenticationConverter auth = new JwtAuthenticationConverter();
		auth.setJwtGrantedAuthoritiesConverter(jwt -> { 
			System.out.println(jwt.getClaims());
			return Arrays.asList(new SimpleGrantedAuthority("User"));});
		return auth;
	}
}
