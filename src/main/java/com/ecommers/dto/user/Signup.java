package com.ecommers.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signup {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
