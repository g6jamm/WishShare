package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishListRepository {

  Connection connection = DbManager.getInstance().getConnection();

  /**
   * Method to add a wishlist to database. @Returns if succedful return Index position of generated
   * entry else returns 0.
   *
   * @auther Andreas
   */
  public int addWishList(String name, int userid) {

    try {
      String stm = "INSERT INTO wishlist (name, user_id) VALUES (? , ?)";
      PreparedStatement ps = connection.prepareStatement(stm, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, name);
      ps.setInt(2, userid);
      ps.executeUpdate();
      ResultSet result = ps.getGeneratedKeys();
      if (result.next()) {
        return result.getInt(1);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return 0;
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

  public Wishlist updateName(int id, String name) {
    try {
      String stm = "UPDATE wishlist SET name = ? WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(stm, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, name);
      ps.setInt(2, id);
      ResultSet rs = ps.executeQuery();
      Wishlist wishlist;
      if (rs.next()) {
        wishlist =
            new Wishlist.WishListBuilder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .userid(rs.getInt(3))
                .build();
        return wishlist;
      }

    } catch (SQLException e) {
      // do something
    }
    return null;
  }

  /**
   * @param id id of wishlist to be deleted
   * @return True if succesful, false if not.
   */
  public boolean deleteWishlist(int id) {
    try {
      if (deleteWishes(id)) {
        String stm = "DELETE FROM wishlist WHERE wishlist_id = ?";
        PreparedStatement ps = connection.prepareStatement(stm);
        ps.setInt(1, id);
        ps.executeUpdate();
        return true;
      }
    } catch (SQLException e) {
      return false;
    }
    return false;
  }

  /**
   * @param wishlist_id id of wishlist to be deleted
   * @return True if succesful, false if not.
   */
  private boolean deleteWishes(int wishlist_id) {
    try {
      String stm = "DELETE FROM wish WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(stm);
      ps.setInt(1, wishlist_id);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
}
