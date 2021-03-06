package com.timhappyjava.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
		//a weak no encrypt password method to compare
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		//add bcypt encoder
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
	}

	//new configure method for role base session
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/delete_merchant/**","/new_merchant").hasAuthority("ADMIN_AUTHORITY")
			.antMatchers("/edit_merchant/**").hasAnyAuthority("ADMIN_AUTHORITY","WRITE_AUTHORITY")
			.antMatchers(HttpMethod.POST, "/merchant").hasAnyAuthority("ADMIN_AUTHORITY","WRITE_AUTHORITY")
			.antMatchers(HttpMethod.GET, "/merchant").hasAnyAuthority("ADMIN_AUTHORITY","WRITE_AUTHORITY","READ_AUTHORITY")
			.antMatchers(HttpMethod.POST, "/player").hasAnyAuthority("ADMIN_AUTHORITY","WRITE_AUTHORITY")
			.antMatchers(HttpMethod.GET, "/player").hasAnyAuthority("ADMIN_AUTHORITY","WRITE_AUTHORITY","READ_AUTHORITY")
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.defaultSuccessUrl("/",true).failureUrl("/login?failed=true")
			.and()
			.logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/logout-success").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/deny");
		//.exceptionHandling().authenticationEntryPoint(new CustomEntryPoint());
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
