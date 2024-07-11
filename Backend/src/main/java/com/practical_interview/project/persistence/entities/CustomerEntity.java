package com.practical_interview.project.persistence.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.practical_interview.project.persistence.entities.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "internal_id")
    private UUID Id;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "customer_id", unique = true)
    @NotBlank
    private String userID;

    @Column(name = "customer_pin")
    @NotBlank
    private String userPin;

    @Column(name = "customer_type")
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "accounts", fetch = FetchType.EAGER)
    public AccountEntity account;

    @OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
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
        return userID;
    }

    public String getCustomerName() {
        return String.join(" ", firstName, lastName);
    }
}
