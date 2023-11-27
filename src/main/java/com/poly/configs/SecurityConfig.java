package com.poly.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeRequests((authorizeRequests) -> authorizeRequests
						.requestMatchers("/cart", "/order", "/rest/cart/add/**", "/profile",
						"/profile/edit")
						.authenticated()
						.requestMatchers("/home", "/about", "/service", "/contact","/admin","/admin/account",
						"/rest/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN"))
						// .requestMatchers("/home", "/about", "/service", "/contact", "/rest/**", "/admin/**", "/cart",
						// 		"/order", "/rest/cart/add/**", "/profile", "/profile/edit")
						// .permitAll())
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.failureUrl("/login/?error=true"))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.deleteCookies("JSESSIONID"));

		return http.build();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
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
