package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.exception.LoginException;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;

public class UserService {

  private final UserRepository USER_REPOSITORY;

  public UserService(UserRepository USER_REPOSITORY) {
    this.USER_REPOSITORY = USER_REPOSITORY;
  }

  public User getUser(Object id) {
    return USER_REPOSITORY.getUser((Integer) id);
  }

  public boolean isValidUser(Object id) {
    if (null != id) {
      return userExist((Integer) id);
    }
    return false;
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

  public User login(String email, String password) throws LoginException {
    return USER_REPOSITORY.login(email, password);
  }


  public boolean userExist(int id) {
    return USER_REPOSITORY.userExists(id);
  }

}
