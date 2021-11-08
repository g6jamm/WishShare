package com.group6.wishshare.domain.service;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;

/**
 * Facade to a datasource layer.
 *
 * @auther Mathias
 */
public class LoginService {
  private final DataFacadeInterface FACADE;

  public LoginService(DataFacadeInterface facade) {
    this.FACADE = facade;
  }

  public User login(String email, String password) throws LoginException {
    return FACADE.login(email, password);
  }


  /**
   *
   * @return New User with id generated from database.
   * @throws LoginException
   */

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

    User result = FACADE.createUser(user);

    return result;
  }
}
