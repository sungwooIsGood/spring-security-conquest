package io.security.springsecuritymaster.users.controller;

import io.security.springsecuritymaster.users.domain.dto.AccountDto;
import io.security.springsecuritymaster.users.domain.entity.Account;
import io.security.springsecuritymaster.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(AccountDto accountDto){
        ModelMapper mapper = new ModelMapper();
        Account account = mapper.map(accountDto, Account.class);

        // 비밀번호 암호화
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        userService.createUser(account);

        return "ddredirect:/";
    }
}
