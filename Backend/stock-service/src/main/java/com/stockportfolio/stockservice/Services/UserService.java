package com.stockportfolio.stockservice.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.stockportfolio.stockservice.Exceptions.UserPasswordMismatchException;
import com.stockportfolio.stockservice.Models.User;
import com.stockportfolio.stockservice.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserPasswordMismatchException();
        }
        return user;
    }

}
