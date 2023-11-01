package com.dudka.store.entity;


import com.dudka.store.util.constants.ApplicationConstants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users" ,
        uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phone"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = ApplicationConstants.Web.DataValidation.MIN_SIZE_OF_FIRSTNAME,
            max = ApplicationConstants.Web.DataValidation.MAX_SIZE_OF_FIRSTNAME)
    @Column(name = "firstname")
    @NotNull
    private String firstname;

    @Column(name = "surname")
    @Size(min = ApplicationConstants.Web.DataValidation.MIN_SIZE_OF_SURNAME,
            max = ApplicationConstants.Web.DataValidation.MAX_SIZE_OF_SURNAME)
    private String surname;

    @Size(min = ApplicationConstants.Web.DataValidation.MIN_SIZE_OF_EMAIL,
            max = ApplicationConstants.Web.DataValidation.MAX_SIZE_OF_EMAIL)
    @Column(name = "email")
    private String email;
    @Size(min = ApplicationConstants.Web.DataValidation.MIN_SIZE_OF_PHONE_NUMBER,
            max = ApplicationConstants.Web.DataValidation.MAX_SIZE_OF_PHONE_NUMBER)
    @Column(name = "phone")
    private String phone;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "user")
    @JsonBackReference
    private List<OrderDetails> orderDetails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Role role;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return (int) (37 * this.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", orderDetails=" + orderDetails +
                ", role=" + role +
                '}';
    }
}
