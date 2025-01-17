package nowicki.piotr.spring_boot_docker.service;

import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.auth.AuthenticationRequest;
import nowicki.piotr.spring_boot_docker.auth.AuthentincationResponse;
import nowicki.piotr.spring_boot_docker.auth.RegisterRequest;
import nowicki.piotr.spring_boot_docker.config.JwtService;
import nowicki.piotr.spring_boot_docker.model.Role;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthentincationResponse register(RegisterRequest request){
        var user = User.builder().name(request.getName()).password(passwordEncoder.encode(request.getPassword())).email(request.getEmail()).role(Role.USER).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthentincationResponse.builder().token(jwtToken).build();
    }

    public AuthentincationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword()));
        var user = userRepository.findByNameLike(request.getName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthentincationResponse.builder().token(jwtToken).build();
    }
}
