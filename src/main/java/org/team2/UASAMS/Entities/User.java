package org.team2.UASAMS.Entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.team2.UASAMS.Entities.Enums.UserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private boolean enabled;

    @Column(unique = true)
    @NotNull 
    private String username;

    @Column(unique = true)
    @NotNull 
    private String email;

    @NotBlank 
    @NotEmpty 
    @NotNull 
    private String password;

    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "reviewer")
    private List<ApplicationReview> conductedReviews = new ArrayList<>();

    @OneToMany(mappedBy = "designatedPanelUser")
    private List<AwardSelection> decidedAwards = new ArrayList<>();
    
    @Override public String getUsername() { return this.email; }
    @Override public String getPassword() { return this.password; }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}