package com.stockportfolio.stockservice.Classes.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {
    private String username;

    private String email;

    private String password;

    private String panNumber;

    private String phoneNumber;
}
