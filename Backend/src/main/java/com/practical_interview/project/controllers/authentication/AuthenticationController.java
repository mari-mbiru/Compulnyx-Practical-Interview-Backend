package com.practical_interview.project.controllers.authentication;

import com.practical_interview.project.controllers.authentication.models.AuthenticationRequest;
import com.practical_interview.project.controllers.authentication.models.AuthenticationResponse;
import com.practical_interview.project.controllers.authentication.models.RegisterRequest;
import com.practical_interview.project.domain.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.practical_interview.project.config.ConfigConstants.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_URL + "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }
}
