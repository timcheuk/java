package com.timhappyjava.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailService;
	
	@Bean
	public AuthenticationProvider authProvider() {
		//Database access object 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		//database store the password string for easy test but it is not security
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		//add bcypt encoder
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		http
			.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/home",true).failureUrl("/login?failed=true")
			.and()
			.logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/logout-success").permitAll();
	}
	
	
//	 @Bean
//	 @Override 
//	 protected UserDetailsService userDetailsService() {
//		 
//	 List<UserDetails> users = new ArrayList<>();
//	 users.add(User.withDefaultPasswordEncoder().username("timcheuk").password(
//	 "1qaZ2wsX").roles("USER").build()); 
//	 
//	 return new InMemoryUserDetailsManager(users); 
//	 }
	 

}
