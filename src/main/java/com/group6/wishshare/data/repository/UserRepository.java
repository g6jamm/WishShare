package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;
import com.group6.wishshare.domain.service.LoginException;
import java.sql.Date;
import java.sql.*;

public class UserRepository {

  /**
   * @return Index value of the newly created user, if nothing was created returns 0.
   * @throws LoginException
   */
  public int createUser(User user) throws LoginException {
    try {
      Connection connection = DbManager.getInstance().getConnection();
      String SQL =
          "INSERT INTO user (email, password, first_name, last_name, gender, birthdate) VALUES "
              + "(?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, user.getEmail());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getFirstName());
      ps.setString(4, user.getLastName());
      ps.setString(5, user.getGender().name());
      ps.setString(6, String.valueOf(Date.valueOf(user.getBirthdate())));
      ps.executeUpdate();
      ResultSet ids = ps.getGeneratedKeys();
      if (ids.next()) {
        return ids.getInt(1);
      }
      return 0;
    } catch (SQLException e) {
      throw new LoginException(e.getMessage());
    }
  }

  public User login(String email, String password) throws LoginException {
    try {
      Connection con = DbManager.getInstance().getConnection();
      String SQL = "SELECT user_id FROM user WHERE email=? AND password=?";
      PreparedStatement ps = con.prepareStatement(SQL);
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

  public boolean userExist(int userId) {
    try {
      Connection con = DbManager.getInstance().getConnection();
      String SQL = "SELECT * FROM user WHERE user_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return true;
      }
      return false;
    } catch (SQLException e) {
      // do something
    }
    return false;
  }

  public User getUser(int user_id) {
    try {
      Connection con = DbManager.getInstance().getConnection();
      String SQL = "SELECT * FROM user WHERE user_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, user_id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new User.UserBuilder()
            .id(user_id)
            .firstName(rs.getString(2))
            .lastName(rs.getString(3))
            .gender(Gender.valueOf(rs.getString(6)))
            .email(rs.getString(4))
            .password(rs.getString(7))
            .birthdate(rs.getDate(5).toLocalDate())
            .build();
      }
    } catch (SQLException e) {
      // do something
    }
    return null;
  }
}
