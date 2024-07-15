package com.practical_interview.project.services.authentication;

import com.practical_interview.project.config.JwtService;
import com.practical_interview.project.config.Utils;
import com.practical_interview.project.controllers.dtos.*;
import com.practical_interview.project.exceptions.AppException;
import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.CustomerEntity;
import com.practical_interview.project.persistence.entities.TokenEntity;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.CustomerRepository;
import com.practical_interview.project.persistence.repositories.TokenRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final TokenRespository tokenRespository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;

    public RegistrationResponse register(RegisterRequest request) {

        var userPin = Utils.generateRandomPin();
        var customer = getCustomerFromRegisterRequest(request, userPin);
        var savedCustomer = customerRepository.save(customer);

        if (accountRepository.findByCustomerId(customer.getUserId()).isPresent()) {
            throw new AppException("A user with that Id already exists. Id must be unique", HttpStatus.BAD_REQUEST);
        }
        var account = AccountEntity.builder()
                .accountBalance(0L)
                .customer(customer)
                .build();
        accountRepository.save(account);

        var customerDetail = getCustomerDetailFromCustomerEntity(savedCustomer);
        customerDetail.setCustomerPin(userPin);

        return RegistrationResponse.builder()
                .customerDetail(customerDetail)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserID(),
                            request.getUserPin()
                    )
            );
        } catch (AuthenticationException e) {
            throw new AppException("Authentication failed: check Id or Password", HttpStatus.UNAUTHORIZED);
        }

        var customerEntity = customerRepository.findByUserId(request.getUserID())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        var jwtToken = jwtService.generateToken(customerEntity);
        var refreshToken = jwtService.generateRefreshToken(customerEntity);

        revokeAllUserTokens(customerEntity);
        saveUserToken(customerEntity, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .customerDetail(getCustomerDetailFromCustomerEntity(customerEntity))
                .build();
    }

    private void saveUserToken(CustomerEntity customer, String jwtToken) {
        var token = TokenEntity.builder()
                .customer(customer)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRespository.save(token);
    }

    private void revokeAllUserTokens(CustomerEntity customer) {
        var validUserTokens = tokenRespository.findAllValidTokenByUser(customer.getUuid());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRespository.saveAll(validUserTokens);
    }

    public CustomerDetail getCustomerDetailFromCustomerEntity(CustomerEntity customer) {

        return CustomerDetail.builder()
                .userID(customer.getUserId())
                .userName(customer.getCustomerName())
                .build();
    }

    public CustomerEntity getCustomerFromRegisterRequest(RegisterRequest request, String userPin) {

        return CustomerEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .userId(request.customerId())
                .userPin(passwordEncoder.encode(userPin))
                .build();
    }

}
