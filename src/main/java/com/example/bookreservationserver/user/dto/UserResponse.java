package com.example.bookreservationserver.user.dto;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.aggregate.UserType;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String phoneNum;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType user_type;

    public UserResponse(Long id, String name, String phoneNum, String email, String password) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.password = password;
    }

    public UserResponse(User user){
        this.id = user.getUser_id();
        this.name = user.getName();
        this.phoneNum = user.getPhoneNum();
        this.email = user.getEmail();
        this.password = user.getPassword();
        user_type = user.getUserType();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNum() { return phoneNum; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserType getUser_type() { return user_type; }
}
