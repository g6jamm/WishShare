package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;
import org.springframework.web.context.request.WebRequest;

public class UserService {
  private final UserRepository userRepository = new UserRepository();
  private final LoginService loginService = new LoginService(new DataFacade());

  public User getUser(int userId) {
    return userRepository.getUser(userId);
  }

  public boolean isValidUser(Integer userId) {
    if (userId != null) {
      return loginService.userExist(userId);
    }
    return false;
  }
}
