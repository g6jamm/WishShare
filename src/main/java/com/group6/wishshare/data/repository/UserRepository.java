package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.LoginException;

public interface UserRepository {

  User login(String email, String password) throws LoginException;

  User createUser(User user) throws LoginException;

  boolean userExists(int id);
}
