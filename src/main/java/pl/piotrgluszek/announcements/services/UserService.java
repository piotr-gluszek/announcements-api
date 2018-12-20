package pl.piotrgluszek.announcements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piotrgluszek.announcements.entities.UserEntity;
import pl.piotrgluszek.announcements.repositories.UsersRepository;

import java.util.NoSuchElementException;

@Service
public class UserService implements UserDetailsService {
    public static final String NO_SUCH_USER_DETAILS = "User with username [%s] does not exist";
    public static final String USERNAME_TAKEN = "Username [%s] already taken";
    public static final String NO_SUCH_USER = "User does not exist";
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) {
        UserEntity user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_SUCH_USER_DETAILS, username)));
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER").build();
    }

    public UserEntity findByUsername(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format(NO_SUCH_USER_DETAILS, username)));
    }

    public void create(UserEntity userEntity) {
        if (usersRepository.existsByUsername(userEntity.getUsername()))
            throw new IllegalArgumentException(String.format(USERNAME_TAKEN, userEntity.getUsername()));
        usersRepository.save(userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword())));
    }
    public UserEntity findById(Long id){
        return usersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_USER));
    }
}
