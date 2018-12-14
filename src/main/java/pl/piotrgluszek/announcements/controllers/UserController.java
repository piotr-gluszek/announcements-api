package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrgluszek.announcements.authentication.TokenManager;
import pl.piotrgluszek.announcements.entities.UserEntity;
import pl.piotrgluszek.announcements.model.ApiMessage;
import pl.piotrgluszek.announcements.services.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenManager jwtTokenManager;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserEntity user) {
        String token;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    ));

            token = jwtTokenManager.generateToken(userService.findByUsername(user.getUsername()).getId());
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiMessage(ex.getMessage()));
        }
        return ResponseEntity.ok(new ApiMessage(token));
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserEntity user) {
        try {
            userService.create(user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiMessage(e.getMessage()));
        }

    }

}
