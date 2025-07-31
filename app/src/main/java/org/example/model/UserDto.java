package org.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.example.entities.User;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto extends User {

    private String userName;

    private String lastName;

    private String phoneNumber;

    private String email;
}
