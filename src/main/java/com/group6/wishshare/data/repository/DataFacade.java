package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.DataFacadeInterface;
import com.group6.wishshare.domain.service.LoginException;

public class DataFacade implements DataFacadeInterface {
  private final UserRepository USER_REPOSITORY = new UserRepository();

  public User login(String email, String password) throws LoginException {
    return USER_REPOSITORY.login(email, password);
  }

  public User createUser(User user) throws LoginException {
    USER_REPOSITORY.createUser(user);

    return user;
  }
}
