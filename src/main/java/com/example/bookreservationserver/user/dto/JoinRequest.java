package com.example.bookreservationserver.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class JoinRequest {
    @NotBlank
    @Length(min = 3, max = 10)
    private String name;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])-?(\\d{3}|\\d{4})-?(\\d{4})")
    private String phoneNum;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 16)
    private String password;
}