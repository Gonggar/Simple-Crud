package edu.bjtu.sei.simplecrud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.bjtu.sei.simplecrud.domain.ERole;
import edu.bjtu.sei.simplecrud.security.jwt.AuthEntryPointJwt;
import edu.bjtu.sei.simplecrud.security.jwt.AuthTokenFilter;
import edu.bjtu.sei.simplecrud.security.services.UserDetailsServiceImpl;
import edu.bjtu.sei.simplecrud.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    //
    //
//	@Autowired
//	UserDetailsServiceImpl userDetailsService;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

    @Configuration
    @Order(1)
    public static class apiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthTokenFilter jwtauthFilter;
        
    	@Autowired
    	UserDetailsServiceImpl userDetailsService;
    
//        @Bean
//        public BCryptPasswordEncoder passwordEncoder(){
//            return new BCryptPasswordEncoder();
//        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http
                .csrf().disable()
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").hasAnyRole("API")
            .and()
                .addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    	
    	@Override
    	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    	}

    	@Bean
    	@Override
    	public AuthenticationManager authenticationManagerBean() throws Exception {
    		return super.authenticationManagerBean();
    	}

//    	@Override
//    	protected void configure(HttpSecurity http) throws Exception {
//    		http.cors().and().csrf().disable()
//    			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//    			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//    			.authorizeRequests().antMatchers("/api/**", "/api/auth/**").hasRole("API");
// //   			.antMatchers("/api/**").permitAll();
// //   			.anyRequest().authenticated();
//
//    		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    	}
    }
    
    
    @Configuration
    @Order(2)
	public static class webSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService userService;
        
//        @Bean
//        public BCryptPasswordEncoder passwordEncoder(){
//            return new BCryptPasswordEncoder();
//        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(userService);
            auth.setPasswordEncoder(passwordEncoder());
            return auth;
        }

		    @Override
		    protected void configure(HttpSecurity http) throws Exception {
		        http
		                .authorizeRequests()
		                    .antMatchers(
		                            "/registration**",
		                            "/js/**",
		                            "/css/**",
		                            "/img/**",
		                            "/webjars/**",
		                            "/swagger-ui/**").permitAll()
		                    .anyRequest().authenticated()
		                .and()
		                    .formLogin()
		                        .loginPage("/login")
		                            .permitAll()
		                .and()
		                    .logout()
		                        .invalidateHttpSession(true)
		                        .clearAuthentication(true)
		                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		                        .logoutSuccessUrl("/login?logout")
		                        .permitAll()
		                .and()
		                	.csrf().disable();
		    }
		
		
		    @Override
		    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		        auth.authenticationProvider(authenticationProvider());
		    }

    }


}
