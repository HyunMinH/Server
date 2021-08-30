package com.example.bookreservationserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    private String password;

}
