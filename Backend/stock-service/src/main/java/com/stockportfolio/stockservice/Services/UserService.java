package com.stockportfolio.stockservice.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.hibernate.annotations.DialectOverride.OverridesAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stockportfolio.stockservice.Exceptions.UserPasswordMismatchException;
import com.stockportfolio.stockservice.Models.PendingUser;
import com.stockportfolio.stockservice.Models.User;
import com.stockportfolio.stockservice.Repositories.PendingVerificationRepository;
import com.stockportfolio.stockservice.Repositories.UserRepository;
import com.stockportfolio.stockservice.Security.UserInfoDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements UserServiceInterface, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PendingVerificationRepository pendingVerificationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        return existingUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User authenticateByEmail(String email, String password) throws UserPasswordMismatchException {
        Optional<User> user = userRepository.findByEmail(email);
        User existingUser = user.get();
        if (existingUser == null) {
            return null;
        }
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new UserPasswordMismatchException();
        }
        return existingUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByEmail(username);// We find using email and not username
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public boolean existsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
        } else {
            existingUser = null;
        }
        return existingUser != null;
    }

    public boolean existsByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
        } else {
            existingUser = null;
        }
        return existingUser != null;
    }

    public List<PendingUser> getAllPendingVerificationUsers() {
        return pendingVerificationRepository.findAll();
    }

}
