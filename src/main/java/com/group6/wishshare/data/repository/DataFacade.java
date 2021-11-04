package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.DataFacadeInterface;
import com.group6.wishshare.domain.service.LoginException;

public class DataFacade implements DataFacadeInterface {
  private final userRepository userRepository = new userRepository();

  public User login(String email, String password) throws LoginException {
    return userRepository.login(email, password);
  }

  public User createUser(User user) throws LoginException {
    userRepository.createUser(user);

    return user;
  }
}
