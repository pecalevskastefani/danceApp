package com.example.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
public class User implements UserDetails
{
    @Id
    String username;
    String name;
    String surname;
    String password;

    LocalDate birthday;
    @ManyToOne(cascade = CascadeType.ALL)
    Program program;
    @Column(nullable = true, length = 64)
    private String photos;
    @Transient
    public String getPhotosImagePath() {
        if (photos == null || username == null) return null;

        return "/user-photos/" + username + "/" + photos;
    }

    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    public User() {
    }
    public User(String name, String surname, String username, String password, Role role, LocalDate birthday,Program program) {
        this.name = name;
        this.surname = surname;
        this.username=username;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
       this.program=program;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}