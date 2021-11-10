package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;

public class UserService {

  UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUser(Object id) {
    return userRepository.getUser((Integer) id);
  }

  public boolean isValidUser(Object id) {
    if (null != id) {
      return new LoginService(userRepository).userExist((Integer) id);
    }
    return false;
  }
}
