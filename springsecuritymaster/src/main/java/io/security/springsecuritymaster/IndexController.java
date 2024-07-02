package io.security.springsecuritymaster;

import lombok.Getter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/anonymous")
    public String anonymous(){
        // 익명 이용자만 해당 API에 접근 가능하도록 config에서 설정한 것이다.
        return "anonymous";
    }

    @GetMapping("/authentication")
    public String authentication(Authentication authentication){
        if(authentication instanceof AnonymousAuthenticationToken){
            return "anonymous";
        } else{
            return "null";
        }
    }

    @GetMapping("/anonymousContext")
    public String anonymousContext(@CurrentSecurityContext SecurityContext securityContext){
        return securityContext.getAuthentication().getName();
    }
}
