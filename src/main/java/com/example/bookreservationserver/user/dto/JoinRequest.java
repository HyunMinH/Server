package com.example.bookreservationserver.user.dto;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
public class JoinRequest {
    @NotBlank
    @Length(min = 3, max = 10)
    private String name;

    @NotBlank
    @Pattern(regexp = "[0-9]{10,11}")
    private String phoneNum;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    private String password;

    public JoinRequest(String name, String phoneNum, String email, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}