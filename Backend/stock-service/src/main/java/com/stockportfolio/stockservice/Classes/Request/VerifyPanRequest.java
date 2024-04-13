package com.stockportfolio.stockservice.Classes.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyPanRequest {
    private String email;
}
