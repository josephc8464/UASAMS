package org.team2.UASAMS.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Entities.Enums.UserRole;
import org.team2.UASAMS.Repos.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Handles user authentication context lookups, ownership checks, and account registration
 * within the UASAMS system.
 *
 * @author Team 2
 * @since 11/25/26
 * @see User
 * @see UserRepository
 */
@Service 
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an {@code AuthService} with the required user repository and password encoder.
     *
     * @param userRepository  repository for querying and persisting {@link User} accounts
     * @param passwordEncoder encoder used to hash raw passwords before saving
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves the currently authenticated {@link User} from the Spring Security context.
     *
     * @return the logged-in {@link User} entity
     * @throws RuntimeException if no matching user record is found in the database
     */
    public User getLoggedInProfile() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Logged-in profile not found"));
    }

    /**
     * Checks whether the currently logged-in user matches the given target user.
     *
     * @param target the {@link User} account to compare against the current session
     * @return {@code true} if the target user is the authenticated user, {@code false} otherwise
     */
    public boolean isOwner(User target) {
        return target.getUsername().equals(getLoggedInProfile().getUsername());
    }

    /**
     * Validates uniqueness constraints and registers a new enabled {@link User} account.
     *
     * @param username  desired unique display username
     * @param email     desired unique email address
     * @param password  raw plaintext password to be encoded
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param role      role string parsed via {@link UserRole#fromString(String)}
     * @throws IllegalArgumentException if the username, email, or both are already registered
     */
    @Transactional
    public void signupUser(String username, String email, String password, String firstName, String lastName, String role) {
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
        user.addRole(UserRole.fromString(role));
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);

        userRepository.save(user); 
    }
}