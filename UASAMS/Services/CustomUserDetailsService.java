package org.team2.UASAMS.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.team2.UASAMS.Entities.User;
import org.team2.UASAMS.Repos.UserRepository;

/**
 * Custom Spring Security {@link UserDetailsService} implementation for loading {@link User}
 * accounts from the database during authentication.
 *
 * @author Team 2
 * @since 11/25/26
 * @see User
 * @see UserRepository
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a {@code CustomUserDetailsService} with the required user repository.
     *
     * @param userRepository repository for looking up {@link User} accounts
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates a user record by their login identifier for Spring Security authentication.
     *
     * @param username the username (or email) submitted during login
     * @return the matching {@link UserDetails} instance
     * @throws UsernameNotFoundException if no matching user exists in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}