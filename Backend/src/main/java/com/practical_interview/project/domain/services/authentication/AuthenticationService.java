package com.practical_interview.project.domain.services.authentication;

import com.practical_interview.project.config.JWTService;
import com.practical_interview.project.config.Utils;
import com.practical_interview.project.controllers.authentication.models.AuthenticationRequest;
import com.practical_interview.project.controllers.authentication.models.AuthenticationResponse;
import com.practical_interview.project.controllers.authentication.models.RegisterRequest;
import com.practical_interview.project.domain.models.CustomerDetail;
import com.practical_interview.project.persistence.entities.CustomerEntity;
import com.practical_interview.project.persistence.entities.TokenEntity;
import com.practical_interview.project.persistence.entities.enums.TokenType;
import com.practical_interview.project.persistence.entities.enums.Role;
import com.practical_interview.project.persistence.repositories.CustomerRespository;
import com.practical_interview.project.persistence.repositories.TokenRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRespository customerRespository;
    private final TokenRespository tokenRespository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var customer = getCustomerFromRegisterRequest(request);
        var savedCustomer = customerRespository.save(customer);

        var customerDetail = getCustomerDetailFromCustomerEntity(savedCustomer);

        var jwtToken = jwtService.generateToken(savedCustomer);
        var refreshToken = jwtService.generateRefreshToken(savedCustomer);
        saveUserToken(savedCustomer, jwtToken);
        return AuthenticationResponse.builder()
                .customerDetail(customerDetail)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserID(),
                        request.getUserPin()
                )
        );

        var customerEntity = customerRespository.findByUserID(request.getUserID())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(customerEntity);
        var refreshToken = jwtService.generateRefreshToken(customerEntity);

        revokeAllUserTokens(customerEntity);
        saveUserToken(customerEntity, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(CustomerEntity customer, String jwtToken) {
        var token = TokenEntity.builder()
                .customer(customer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRespository.save(token);
    }

    private void revokeAllUserTokens(CustomerEntity customer) {
        var validUserTokens = tokenRespository.findAllValidTokenByUser(customer.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRespository.saveAll(validUserTokens);
    }

    //    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }
    public CustomerDetail getCustomerDetailFromCustomerEntity(CustomerEntity customer) {

        return CustomerDetail.builder()
                .userID(customer.getUserID())
                .userName(customer.getUsername())
                .build();
    }

    public CustomerEntity getCustomerFromRegisterRequest(RegisterRequest request) {

        return CustomerEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .userPin(passwordEncoder.encode(Utils.generateRandomPin()))
                .role(Role.CUSTOMER)
                .build();
    }

}
