package org.team2.UASAMS.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Repos.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class AuthService {
    
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getLoggedInProfile() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in profile not found"));
    }

    public boolean isOwner(User target) {
        return target.getUsername().equals(getLoggedInProfile().getUsername());
    }

    @Transactional
    public void signupUser(String username, String email, String password, String firstName, String lastName) {
        boolean emailExists = userRepository.findByEmail(email).isPresent();
        boolean usernameExists = userRepository.findByUsername(username).isPresent();

        if (emailExists && usernameExists) {
            throw new IllegalArgumentException("Username and Email already in use");
        } else if (emailExists) {
            throw new IllegalArgumentException("Email is already in use");
        } else if (usernameExists) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user); 
    }
}
