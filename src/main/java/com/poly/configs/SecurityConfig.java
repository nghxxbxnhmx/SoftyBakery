package com.poly.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.poly.models.Account;
import com.poly.services.AccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	AccountService accountService;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/cart", "/order", "/rest/cart/add/**", "/profile", "/profile/edit", "order-history")
						.authenticated()
						.requestMatchers("/manage/**").hasAnyRole("MANAGER", "ADMIN", "SUPER_ADMIN")
						.requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
						.requestMatchers("/superadmin/**").hasRole("SUPER_ADMIN")
						.anyRequest().permitAll())
						
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.failureUrl("/login/?error=true"))

				.logout(logout -> logout
						.logoutUrl("/logout")
						.deleteCookies("JSESSIONID"))

				.oauth2Login(oauth2 -> oauth2
						.loginPage("/oauth/login/form")
						.defaultSuccessUrl("/oauth2/login/success", true)
						.failureUrl("/oauth2/login/error")
						.authorizationEndpoint(auth -> auth
								.baseUri("/oauth2/authorization")
								.authorizationRequestRepository(getRepository()))
						.tokenEndpoint(token -> token
								.accessTokenResponseClient(getToken())));

		return http.build();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
		return new DefaultAuthorizationCodeTokenResponseClient();
	}

	@Bean
	public static UserDetails getUser() {
		UserDetails uDetail = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof UserDetails) {
			uDetail = (UserDetails) auth.getPrincipal();
		}
		return uDetail;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
