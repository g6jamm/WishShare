package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.UserRepositoryStub;
import com.group6.wishshare.data.repository.UserRepositoryImpl;
import com.group6.wishshare.domain.model.User;

public class UserService {

  public User getUser(Object id) {
    return new UserRepositoryImpl().getUser((Integer) id);
  }

  public boolean isValidUser(Object id) {
    if (null != id) {
      return new LoginService(new UserRepositoryStub()).userExist((Integer) id);
    }
    return false;
  }
}
