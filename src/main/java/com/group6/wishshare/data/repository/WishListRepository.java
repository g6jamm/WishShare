package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishListRepository {

  Connection connection = DbManager.getInstance().getConnection();

  /**
   * Method to add a wishlist to database. @Returns true if successful, false if not.
   *
   * @auther Andreas
   */
  public boolean addWishList(String name, int userid) {

    try {
      String stm = "INSERT INTO wishlist (name, user_id) VALUES (? , ?)";
      PreparedStatement ps = connection.prepareStatement(stm);
      ps.setString(1, name);
      ps.setInt(2, userid);

      boolean result = ps.execute();

      return result;

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  /** @auther Jackie og Mohamad */
  public boolean isListOwnedByUser(int wishlistId, int userId) {
    boolean result = false;

    String sqlQuery = "SELECT * FROM wishlist WHERE wishlist_id = ? AND user_id = ?;";
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    try {
      preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sqlQuery);
      preparedStatement.setInt(1, wishlistId);
      preparedStatement.setInt(2, userId);

      resultSet = preparedStatement.executeQuery();
      result = resultSet.next();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  /**
   * Method to retrieve a wishlist object from database using wishlist index. @Returns wishlist
   * object or null - i dont like this tho...
   *
   * @auther Andreas
   */
  public Wishlist getWishlist(int id) {

    try {
      String stm = "SELECT * FROM wishlist WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(stm);
      Wishlist wishlist;
      ps.setInt(1, id);
      ResultSet resultSet = ps.executeQuery();
      if (resultSet.next()) {
        wishlist =
            new Wishlist.WishListBuilder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .userid(resultSet.getInt(3))
                .build();
        return wishlist;
      }

    } catch (SQLException e) {
      // do something
    }
    return null;
  }

  public List<Wishlist> getWishLists(int user_id) {
    List<Wishlist> wishlists = new ArrayList<>();

    try {
      String stm = "SELECT * FROM wishlist WHERE user_id = ?";
      PreparedStatement ps = connection.prepareStatement(stm);
      ps.setInt(1, user_id);
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()) {
        wishlists.add(
            new Wishlist.WishListBuilder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .userid(resultSet.getInt(3))
                .build());
      }
    } catch (SQLException e) {
      // do something
    }
    return wishlists;
  }
}
