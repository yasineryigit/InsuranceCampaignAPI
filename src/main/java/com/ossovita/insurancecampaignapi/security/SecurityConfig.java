package com.ossovita.insurancecampaignapi.security;

import com.ossovita.insurancecampaignapi.security.jwt.AuthTokenFilter;
import com.ossovita.insurancecampaignapi.security.jwt.JWTAccessDeniedHandler;
import com.ossovita.insurancecampaignapi.security.jwt.JwtAuthenticationEntryPoint;
import com.ossovita.insurancecampaignapi.security.jwt.JwtUtils;
import com.ossovita.insurancecampaignapi.utilities.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;


    //create & inject authentication manager to the ioc container
    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers().frameOptions().disable().and()
                .csrf().disable()
                .cors()
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)//assign custom access denied handler
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationJwtTokenFilter(jwtUtils, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    //fully disable from security control
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(SecurityConstants.getIgnoringUrls().toArray(String[]::new));
    }


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        return new AuthTokenFilter();
    }


}