package com.kcterala.expensetrackerapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {
    @NotBlank(message = "Please provide a name")
    private String name;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be atleast five characters")
    private String password;

    private Long age = 0L;
}
