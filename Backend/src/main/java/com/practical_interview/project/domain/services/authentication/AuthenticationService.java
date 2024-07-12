package com.practical_interview.project.domain.services.authentication;

import com.practical_interview.project.config.JWTService;
import com.practical_interview.project.config.Utils;
import com.practical_interview.project.controllers.models.AuthenticationRequest;
import com.practical_interview.project.controllers.models.AuthenticationResponse;
import com.practical_interview.project.controllers.models.RegisterRequest;
import com.practical_interview.project.controllers.models.RegistrationResponse;
import com.practical_interview.project.domain.models.CustomerDetail;
import com.practical_interview.project.domain.services.AccountService;
import com.practical_interview.project.persistence.entities.AccountEntity;
import com.practical_interview.project.persistence.entities.CustomerEntity;
import com.practical_interview.project.persistence.entities.TokenEntity;
import com.practical_interview.project.persistence.entities.enums.Role;
import com.practical_interview.project.persistence.repositories.AccountRepository;
import com.practical_interview.project.persistence.repositories.CustomerRepository;
import com.practical_interview.project.persistence.repositories.TokenRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final TokenRespository tokenRespository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    public RegistrationResponse register(RegisterRequest request) {

        var userPin = Utils.generateRandomPin();
        var customer = getCustomerFromRegisterRequest(request, userPin);
        var savedCustomer = customerRepository.save(customer);

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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserID(),
                        request.getUserPin()
                )
        );

        var customerEntity = customerRepository.findByUserId(request.getUserID())
                .orElseThrow();

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
