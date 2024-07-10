package com.practical_interview.project.persistence.entities;

import java.util.UUID;

import com.practical_interview.project.persistence.entities.enums.UserTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {

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
    private UserTypeEnum userType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    public AccountEntity account;

}
