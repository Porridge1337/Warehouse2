package ru.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.example.demo.userDetailsImplementation.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Bean
	public UserDetailsService userDetailsService() { // UserDetailsService
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     authProvider.setUserDetailsService(userDetailsService());
	     authProvider.setPasswordEncoder(passwordEncoder());
         
	        return authProvider;
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
					
					.antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
					.antMatchers("/section").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
					.antMatchers("/section/{id}/update/**").hasAnyAuthority("EDITOR", "ADMIN")
					.antMatchers("/section/add/**").hasAnyAuthority("CREATOR", "ADMIN")
					.antMatchers("/adminPage/**").hasAnyAuthority("ADMIN")
					.antMatchers("/section/products/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
					.antMatchers("/section/{id_sec}/products/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
					.antMatchers("/section/{id_sec}/products/{id}/update/**").hasAnyAuthority("EDITOR", "ADMIN")										
					.antMatchers("/registration/**").anonymous()					
					.antMatchers("/login").anonymous()
				.and()				
					.formLogin().loginPage("/login")
					.loginProcessingUrl("/login/process")
					.defaultSuccessUrl("/section", true)
					.failureUrl("/login?error=true")
				.and()				
					.logout().permitAll()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/")
					
					.and().csrf().disable();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authenticationProvider());					
	}
	
}
