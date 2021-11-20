package com.example.DOTSAPI.model;

import com.example.DOTSAPI.validation.UniqueEmail;
import com.example.DOTSAPI.validation.UniqueUsername;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is missing")
    @Size(max = 40, message = "Password must be smaller than 40 characters long")
    private String firstName;

    @NotBlank(message = "Lastname is missing")
    @Size(max = 40, message = "Password must be smaller than 40 characters long")
    private String lastName;

    @NotBlank(message = "Username is missing")
    @Size(min = 6, max = 20, message = "Username must be between six and 20 characters long")
    @Column(unique = true)
    @UniqueUsername
    private String userName;

    @NotBlank(message = "Email is missing")
    @Size(max = 128, message = "Password must be smaller than 128 characters long")
    @Email(message = "Wrong email format")
    @Column(unique = true)
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is missing")
    @Size(min = 6, message = "Password must be equal or greater than 6 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public AppUser(String firstName, String lastName, String userName, String email, String password, Collection<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return id != null && Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
