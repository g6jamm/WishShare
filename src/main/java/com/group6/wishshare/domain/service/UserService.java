package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;

public class UserService {

  public User getUser(Object id) {
    return new UserRepository().getUser((Integer) id);
  }

  public boolean isValidUser(Object id) {
    if (null != id) {
      return new LoginService(new DataFacade()).userExist((Integer) id);
    }
    return false;
  }
}
