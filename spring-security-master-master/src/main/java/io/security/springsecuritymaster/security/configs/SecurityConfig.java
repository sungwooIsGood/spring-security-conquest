package io.security.springsecuritymaster.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /**
         * csrf 기능은 자동적으로 활성 상태가 된다. 수동으로 끄지 않는 이상
         * post 방식의 요청은 csrf 기능이 켜져있으면 csrf 토큰이 서버가 전달된다.
         */
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**","/images/**","/js/**","/favicon.*","/*/icon-*").permitAll() // 정적 자원 설정
                .requestMatchers("/").permitAll() // root 경로는 전체 이용자 허용
                .anyRequest().authenticated() // 모든 경로는 인증이 필요하다고 설정
        )
        .formLogin(Customizer.withDefaults());// 커스텀 로그인 페이지 경로 설정, 모든 이용자 접근 가능

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return  new InMemoryUserDetailsManager(user);
    }
}
