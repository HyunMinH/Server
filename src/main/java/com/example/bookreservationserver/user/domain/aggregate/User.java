package com.example.bookreservationserver.user.domain.aggregate;

import com.example.bookreservationserver.user.dto.JoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue
    private Long user_id;

    private String name;

    private String phoneNum;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(JoinRequest joinRequest){
        this.name = joinRequest.getName();
        this.phoneNum = joinRequest.getPhoneNum();
        this.email = joinRequest.getEmail();
        setPassword(joinRequest.getPassword());

        // default is student
        userType = UserType.STUDENT;
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

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    private void setPassword(String newPassword){
        String newPw = Objects.requireNonNull(newPassword, "new password is null");
        if(newPw.equals(password)) throw new IllegalArgumentException("new password is same as before");

        password = newPw;
    }
}
