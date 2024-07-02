package io.security.springsecuritymaster.config.form.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class FormLoginSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/",true)
                        .failureForwardUrl("/failed")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler((request,response,authentication) -> {
                            System.out.println("authentication: " + authentication);
                            response.sendRedirect("/home");
                        })
                        .failureHandler(((request, response, exception) -> {
                            System.out.println("exception: " + exception.getMessage());
                            response.sendRedirect("/login");
                        }))
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .alwaysRemember(true) // or false 기본 값은 false이다.
                        .tokenValiditySeconds(3600)
                        .userDetailsService(userDetailsService())
                        .rememberMeParameter("remember")
                        .rememberMeCookieDomain("remember")
                        .key("security")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
