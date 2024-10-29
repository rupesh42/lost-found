package com.rupesh.assignment.lostfound.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Implemented using BasicAuth for validation of user authentication.
 * the values of username and password will be derived from properties file. 
 * @author Rupesh
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${app.security.users[0].username}")
	private String user1Username;

	@Value("${app.security.users[0].password}")
	private String user1Password;

	@Value("${app.security.users[0].role}")
	private String user1Role;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername(user1Username).password(passwordEncoder().encode(user1Password))
				.roles(user1Role).build());
		return manager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(
						requests -> requests.requestMatchers("/lost-found/**").authenticated().anyRequest().permitAll())
				.httpBasic(withDefaults());
		return http.build();
	}

}
