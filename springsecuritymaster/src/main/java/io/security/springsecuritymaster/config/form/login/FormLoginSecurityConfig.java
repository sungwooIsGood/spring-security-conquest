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
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/anonymous").hasRole("GUEST") // 익명 객체만 접근 가능하도록 설정도 가능하다.
                        .requestMatchers("/anonymousContext","/authentication").permitAll() // 익명 객체만 접근 가능하도록 설정도 가능하다.
                        .anyRequest().authenticated())
//                .formLogin(form -> form
//                        .loginPage("/loginPage")
//                        .loginProcessingUrl("/loginProc")
//                        .defaultSuccessUrl("/",true)
//                        .failureForwardUrl("/failed")
//                        .usernameParameter("userId")
//                        .passwordParameter("password")
//                        .successHandler((request,response,authentication) -> {
//                            System.out.println("authentication: " + authentication);
//                            response.sendRedirect("/home");
//                        })
//                        .failureHandler(((request, response, exception) -> {
//                            System.out.println("exception: " + exception.getMessage());
//                            response.sendRedirect("/login");
//                        }))
//                        .permitAll()
//                )
//                .rememberMe(rememberMe -> rememberMe
//                        .alwaysRemember(true) // or false 기본 값은 false이다.
//                        .tokenValiditySeconds(3600)
//                        .userDetailsService(userDetailsService())
//                        .rememberMeParameter("remember")
//                        .rememberMeCookieDomain("remember")
//                        .key("security")
//                )
                .anonymous(anonymous -> anonymous
                        .principal("guest") // 해당 메서드는 보통 사용자 정보가 들어가는데, 여기서는 익명 사용자의 이름을 담는다. / default 값은 anonymousUser 이다.
                        .authorities("ROLE_GUEST") // 익명 사용자를 위한 권한
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
