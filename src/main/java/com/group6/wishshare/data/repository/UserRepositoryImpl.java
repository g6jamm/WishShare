package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.util.DBManager;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;
import com.group6.wishshare.domain.service.LoginException;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository{

  /** @return New User object with generated id from database. */
  @Override
  public User createUser(User user) throws LoginException {
    int id = getNewUserId(user);

    return new User.UserBuilder()
        .id(id)
        .email(user.getEmail())
        .password(user.getPassword())
        .birthdate(user.getBirthdate())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .gender(user.getGender())
        .build();
  }

  /** @return Index value of the newly created user, if nothing was created returns 0. */
  @Override
  public int getNewUserId(User user) throws LoginException {
    try {
      String query =
          "INSERT INTO user (email, password, first_name, last_name, gender, birthdate) VALUES "
              + "(?, ?, ?, ?, ?, ?)";
      PreparedStatement ps =
          DBManager.getInstance()
              .getConnection()
              .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, user.getEmail());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getFirstName());
      ps.setString(4, user.getLastName());
      ps.setString(5, user.getGender().name());
      ps.setString(6, String.valueOf(Date.valueOf(user.getBirthdate())));
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        return rs.getInt(1);
      }

      return 0;
    } catch (SQLException e) {
      throw new LoginException(e.getMessage());
    }
  }

  @Override
  public User login(String email, String password) throws LoginException {
    try {
      String query = "SELECT user_id FROM user WHERE email = ? AND password = ?";

      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setString(1, email);
      ps.setString(2, password);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        int id = rs.getInt("user_id");
        return new User.UserBuilder()
            .email(email)
            .password(password)
            .id(id)
            .build(); // TODO add more?
      } else {
        throw new LoginException("Not a valid user");
      }
    } catch (SQLException e) {
      throw new LoginException(e.getMessage());
    }
  }

  @Override
  public boolean userExists(int id) {
    try {
      String query = "SELECT * FROM user WHERE user_id = ?";
      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setInt(1, id);

      return ps.executeQuery().next();
    } catch (SQLException e) {
      // do something
    }
    return false;
  }

  @Override
  public User getUser(int id) {

    try {
      String query = "SELECT * FROM user WHERE user_id = ?";
      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new User.UserBuilder()
            .id(id)
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .gender(Gender.valueOf(rs.getString("gender")))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .birthdate(rs.getDate("birthdate").toLocalDate())
            .build();
      }
    } catch (SQLException e) {
      // do something
    }

    return null;
  }
}
