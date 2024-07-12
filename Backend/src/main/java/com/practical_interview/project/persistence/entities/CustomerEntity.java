package com.practical_interview.project.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(unique = true)
    @NotBlank
    private String userId;

    @NotBlank
    private String userPin;

    @OneToOne(mappedBy = "customer", fetch = FetchType.EAGER)
    public AccountEntity account;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Collection<TokenEntity> tokenEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return userPin;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    public String getCustomerName() {
        return String.join(" ", firstName, lastName);
    }
}
