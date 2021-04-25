package com.example.bookreservationserver.user.domain.aggregate;

import com.example.bookreservationserver.user.dto.JoinRequest;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
public class User {
    @Id
    @GeneratedValue
    private Long user_id;

    private String name;

    private String phoneNum;

    private String email;

    private String password;


    protected User() {}

    public User(JoinRequest joinRequest){
        this.name = joinRequest.getName();
        this.phoneNum = joinRequest.getPhoneNum();
        this.email = joinRequest.getEmail();
        setPassword(joinRequest.getPassword());
    }

    public User(Long user_id, String name, String phoneNum, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        setPassword(password);
    }

    public void changePassword(String oldPassword, String newPassword){
        if(!matchPassword(oldPassword)) throw new IllegalArgumentException("password not matched");
        setPassword(newPassword);
    }

    private boolean matchPassword(String oldPassword) {
        return password.equals(oldPassword);
    }

    private void setPassword(String newPassword){
        String newPw = Objects.requireNonNull(newPassword, "new password is null");
        if(newPw.equals(password)) throw new IllegalArgumentException("new password is same as before");

        password = newPw;
    }
}
