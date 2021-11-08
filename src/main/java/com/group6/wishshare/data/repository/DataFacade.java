package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.DataFacadeInterface;
import com.group6.wishshare.domain.service.LoginException;

public class DataFacade implements DataFacadeInterface {
  private final UserRepository USER_REPOSITORY = new UserRepository();

  public User login(String email, String password) throws LoginException {
    return USER_REPOSITORY.login(email, password);
  }

  /**
   * @return New User object with generated id from database.
   * @throws LoginException
   */
  public User createUser(User user) throws LoginException {
    int id = USER_REPOSITORY.createUser(user);

    return new User.UserBuilder()
        .id(id)
        .email(user.getEmail())
        .password(user.getPassword())
        .birthdate(user.getBirthdate())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .gender(user.getGender())
        .build();
  }

  @Override
  public boolean userExist(int user_id) {
    return USER_REPOSITORY.userExist(user_id);
  }
}
