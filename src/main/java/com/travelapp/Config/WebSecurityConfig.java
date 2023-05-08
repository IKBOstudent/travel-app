package com.travelapp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String ENCODED_PASSWORD = "{bcrypt}$2a$12$wJKRPuk1OIKHnJnpj0Zp0uwqjFACJBs4SoUj/mV19aozU68JYTrQm";

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(ENCODED_PASSWORD)
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/flights", "/api/rooms").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/reservations", "/api/flights/book").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .formLogin().disable()
                .logout()
                .logoutUrl("/api/admin/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
