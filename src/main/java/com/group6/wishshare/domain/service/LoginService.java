package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;

/**
 * Facade to a datasource layer.
 *
 * @auther Mathias
 */
public class LoginService {

  private final UserRepository USER_REPOSITORY;

  public LoginService(UserRepository userRepository) {
    this.USER_REPOSITORY = userRepository;
  }

  public User login(String email, String password) throws LoginException {
    return USER_REPOSITORY.login(email, password);
  }

  /** @return New User with id generated from database. */
  public User createUser(
      String firstname,
      String lastname,
      LocalDate birthdate,
      Gender gender,
      String email,
      String password)
      throws LoginException {
    User user =
        new User.UserBuilder()
            .firstName(firstname)
            .lastName(lastname)
            .email(email)
            .birthdate(birthdate)
            .gender(gender)
            .password(password)
            .build();

    return USER_REPOSITORY.createUser(user);
  }

  public boolean userExist(int id) {
    return USER_REPOSITORY.userExists(id);
  }
}
