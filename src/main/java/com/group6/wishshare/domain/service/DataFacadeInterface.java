package com.group6.wishshare.domain.service;

import com.group6.wishshare.domain.model.User;

public interface DataFacadeInterface {
  User login(String email, String password) throws LoginException;

  User createUser(User user) throws LoginException;

  boolean userExist(int user_id);
}
