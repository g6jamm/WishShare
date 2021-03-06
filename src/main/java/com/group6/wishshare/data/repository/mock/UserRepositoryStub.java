package com.group6.wishshare.data.repository.mock;

import com.group6.wishshare.data.repository.UserRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.exception.LoginException;

public class UserRepositoryStub implements UserRepository {

  @Override
  public User login(String email, String password) throws LoginException {
    return null;
  }

  @Override
  public User createUser(User user) throws LoginException {
    return null;
  }

  @Override
  public boolean userExists(int id) {
    return false;
  }

  @Override
  public int getNewUserId(User user) throws LoginException {
    return 0;
  }

  @Override
  public User getUser(int id) {
    return null;
  }
}
