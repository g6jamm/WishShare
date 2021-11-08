package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;

public class UserService {
  private UserRepository userRepository = new UserRepository();

  public User getUser(int userId) {
    return userRepository.getUser(userId);
  }
}
