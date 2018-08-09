package com.SpringRestMongoDB.sbsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.SpringRestMongoDB.service.AppUserDetailsService;



@Configurable
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	AppUserDetailsService appUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());
		
	}
	
	@SuppressWarnings("deprecation")
	 public static NoOpPasswordEncoder passwordEncoder() {
	     return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	 }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	          
	        }
	    };
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	 http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/historique").permitAll();
    	 http.cors().and().authorizeRequests().antMatchers(HttpMethod.POST,"/api/historique/create").permitAll();
         http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/historique/{nom}/{dateD}/{dateF}").permitAll();
         http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/historique/{nom}").permitAll();
         http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/historique/echec/{dateD}").permitAll();
    	 http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET,"/api/historique/echec/send/{id}").permitAll();

         
   http.cors().and().authorizeRequests().antMatchers(HttpMethod.PUT,"/api/test/{id}").permitAll();	
   http.cors().and().authorizeRequests().antMatchers(HttpMethod.PUT,"/api/test/**").permitAll();	
   http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/test/{id}").permitAll();

   http.cors().and().authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/test/delete/**").permitAll();	
     http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/Customers/**").permitAll();
     http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/test/**").permitAll();
     http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/customers/create").permitAll();
     http.cors().and().authorizeRequests().antMatchers(HttpMethod.POST,"/api/test/create").permitAll();
     http.cors().and().authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/test/delete/{id}").permitAll();

    	
    	
    	
    	
    	http.cors().and()
	
		.authorizeRequests()
	
		.antMatchers("/account/register","/account/find/{username}","/account/login","/logout","/api/test/lunch","/api/test_date","/api/test_date_lunch").permitAll()
	
		.anyRequest().fullyAuthenticated().and()

		.logout()
        .permitAll()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
        .and()
	
		.httpBasic().and()

		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
	
		.csrf().disable();
    	
    	http.cors().and()
    	
		.authorizeRequests()
	
		.antMatchers("/api/test/lunch").permitAll();

    }
}
