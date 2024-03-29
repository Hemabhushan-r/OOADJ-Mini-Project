package com.stockportfolio.stockservice.Services;

import java.util.List;

import com.stockportfolio.stockservice.Exceptions.UserPasswordMismatchException;
import com.stockportfolio.stockservice.Models.User;

public interface UserServiceInterface {
    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);

    User authenticateByEmail(String email, String password) throws UserPasswordMismatchException;
}
