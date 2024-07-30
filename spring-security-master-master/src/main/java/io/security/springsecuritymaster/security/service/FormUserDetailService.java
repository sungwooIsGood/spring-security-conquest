package io.security.springsecuritymaster.security.service;

import io.security.springsecuritymaster.users.domain.dto.AccountContext;
import io.security.springsecuritymaster.users.domain.dto.AccountDto;
import io.security.springsecuritymaster.users.domain.entity.Account;
import io.security.springsecuritymaster.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("userDetailsService") // userDetailsService으로 빈 주입 시키기 위해
@RequiredArgsConstructor
public class FormUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 인증 시 유저가 있는지 확인하는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = userRepository.findByUsername(username);

        if(Objects.isNull(account)){
            throw new UsernameNotFoundException("No user found with username: " + username); // 시큐리티에서 제공하는 예외
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(account.getRoles()));

        ModelMapper modelMapper = new ModelMapper();

        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        return new AccountContext(accountDto,authorities);
    }
}
