package nowicki.piotr.spring_boot_docker.controller;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationRequest;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationResponse;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
