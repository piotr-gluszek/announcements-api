package pl.piotrgluszek.announcements.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrgluszek.announcements.entities.UserEntity;
import pl.piotrgluszek.announcements.error.ErrorEntity;
import pl.piotrgluszek.announcements.services.UserService;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserEntity user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Is authenticated: " + authentication.isAuthenticated());
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserEntity user) {
        try {
            userService.create(user);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorEntity().setMessage(e.getMessage()));
        }

    }
}
