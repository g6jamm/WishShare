package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.Wish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** @author Jackie, Mohamad */
public class WishRepository {

  public List<Wish> getWishes(int wishlistId) {
    List<Wish> result = new ArrayList<>();
    String sqlQuery = "SELECT * FROM wish WHERE wishlist_id = " + wishlistId;
    ResultSet resultSet = resultSet(sqlQuery);

    try {
      while (resultSet.next()) {
        Wish wish =
            new Wish.WishBuilder()
                .id(resultSet.getInt("wish_id"))
                .name(resultSet.getString("name"))
                .link(resultSet.getString("link"))
                .price(resultSet.getString("price"))
                .wishlistId(resultSet.getInt("wishlist_id"))
                .reserved(resultSet.getBoolean("reserved"))
                .build();
        result.add(wish);
      }
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
    }
    return result;
  }

  public Wish getWish(int wishId) {
    String sqlQuery = "SELECT * FROM wish WHERE wish_id = " + wishId;
    ResultSet resultSet = resultSet(sqlQuery);
    Wish wish = null;

    try {
      while (resultSet.next()) {
        wish =
            new Wish.WishBuilder()
                .id(resultSet.getInt("wish_id"))
                .name(resultSet.getString("name"))
                .link(resultSet.getString("link"))
                .price(resultSet.getString("price"))
                .wishlistId(resultSet.getInt("wishlist_id"))
                .reserved(resultSet.getBoolean("reserved"))
                .build();
      }
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
    }
    return wish;
  }

  public boolean reserveWish(boolean isReserved, int wishId) {
    String sqlQuery = "UPDATE wish SET reserved = ? WHERE wish_id = ?";

    PreparedStatement preparedStatement;
    try {
      preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sqlQuery);
      preparedStatement.setBoolean(1, isReserved);
      preparedStatement.setInt(2, wishId);

      return preparedStatement.execute();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean editWish(String name, String link, String price, int id) {

    String sqlQuery = "UPDATE wish SET name = ?, link = ?, price = ? WHERE wish_id = ?;";

    PreparedStatement preparedStatement;
    try {
      preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sqlQuery);
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, link);
      preparedStatement.setString(3, price);
      preparedStatement.setInt(4, id);
      preparedStatement.executeUpdate();
      return true;

    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
      return false;
    }
  }

  public boolean addWishToWishList(Wish wish) {

    String sqlQuery =
        "INSERT INTO wish(name, link, price, wishlist_id, reserved) VALUES(?,?,?,?,?)";

    PreparedStatement preparedStatement;

    try {
      preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sqlQuery);
      preparedStatement.setString(1, wish.getName());
      preparedStatement.setString(2, wish.getLink());
      preparedStatement.setString(3, wish.getPrice());
      preparedStatement.setInt(4, wish.getWishListId());
      preparedStatement.setBoolean(5, wish.isReserved());

      boolean result = preparedStatement.execute();
      return result;

    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
      return false;
    }
  }

  public ResultSet resultSet(String sql) {

    Connection connection = DbManager.getInstance().getConnection();
    String sqlStatement = sql;
    PreparedStatement preparedStatement;
    ResultSet resultSet = null;

    try {
      preparedStatement = connection.prepareStatement(sqlStatement);
      resultSet = preparedStatement.executeQuery();
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage());
    }
    return resultSet;
  }
}
