package io.security.springsecuritymaster.users.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AccountDto {

    private String id;
    private String username;
    private String password;
    private int age;
    private String roles;

    @Builder
    public AccountDto(String id, String username, String password, int age, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.roles = roles;
    }
}
