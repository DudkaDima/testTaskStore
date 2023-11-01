package com.dudka.store.dto;

import com.dudka.store.entity.Role;
import com.dudka.store.entity.User;
import lombok.Data;

@Data
public class UserRetrieveDto {
    public UserRetrieveDto(User user) {
        this.firstname = user.getFirstname();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    private String firstname;

    private String surname;

    private String email;
    private String phone;

    private String username;
    private Role role;


}
