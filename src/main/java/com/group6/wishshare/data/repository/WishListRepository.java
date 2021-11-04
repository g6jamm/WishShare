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
    String stm = "INSERT INTO wishlist (name, user_id) VALUES (? , ?)";
    PreparedStatement prep;

    try {
      prep = connection.prepareStatement(stm);
      prep.setString(1, name);
      prep.setInt(2, userid);

      boolean result = prep.execute();

      return result;

    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Method to retrieve a wishlist object from database using wishlist index. @Returns wishlist
   * object or null - i dont like this tho...
   *
   * @auther Andreas
   */
  public Wishlist getWishlist(int id) {
    String stm = "SELECT * FROM wishlist WHERE wishlist_id = ?";
    PreparedStatement prep;
    Wishlist wishlist;

    try {
      prep = connection.prepareStatement(stm);
      prep.setInt(1, id);
      ResultSet resultSet = prep.executeQuery();
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

  public List<Wishlist> getWishLists(int user_id){
    String stm = "SELECT * FROM wishlist WHERE user_id = ?";
    PreparedStatement prep;
    List<Wishlist> wishlists = new ArrayList<>();

    try {
      prep = connection.prepareStatement(stm);
      prep.setInt(1, user_id);
      ResultSet resultSet = prep.executeQuery();
      while (resultSet.next()) {
        wishlists.add(
            new Wishlist.WishListBuilder()
                .id(resultSet.getInt(1))
                .name(resultSet.getString(2))
                .userid(resultSet.getInt(3))
                .build()
        );
      }
    } catch (SQLException e) {
      // do something
    }
    return wishlists;
  }

}
