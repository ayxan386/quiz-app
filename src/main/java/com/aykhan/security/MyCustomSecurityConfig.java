package com.aykhan.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class MyCustomSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtFilter jwtFilter;

  public MyCustomSecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .cors()
        .and();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
        .authorizeRequests()
        .antMatchers("/api/**").authenticated()
        .anyRequest().permitAll();

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("HEAD",
        "GET", "POST", "PUT", "DELETE", "PATCH"));
    // setAllowCredentials(true) is important, otherwise:
    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
    configuration.setAllowCredentials(true);
    // setAllowedHeaders is important! Without it, OPTIONS preflight request
    // will fail with 403 Invalid CORS request
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  //provides instance for AuthService
  @Bean
  public AuthenticationManager myAuthenticationManager() throws Exception {
    return super.authenticationManagerBean();
  }
}
