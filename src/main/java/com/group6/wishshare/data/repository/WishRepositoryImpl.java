package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.util.DBManager;
import com.group6.wishshare.domain.model.Wish;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** @author Jackie, Mohamad */
public class WishRepositoryImpl implements WishRepository{

  @Override
  public List<Wish> getWishes(int id) {
    List<Wish> result = new ArrayList<>();

    try {
      String query = "SELECT * FROM wish WHERE wishlist_id = ?";
      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setInt(1, id);

      ResultSet resultSet = ps.executeQuery();

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
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  @Override
  public Wish getWish(int id) {
    try {
      String query = "SELECT * FROM wish WHERE wish_id = ?";
      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return new Wish.WishBuilder()
            .id(rs.getInt("wish_id"))
            .name(rs.getString("name"))
            .link(rs.getString("link"))
            .price(rs.getString("price"))
            .wishlistId(rs.getInt("wishlist_id"))
            .reserved(rs.getBoolean("reserved"))
            .build();
      }
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage()); // TODO
    }

    return null;
  }

  @Override
  public void isReservedWish(boolean isReserved, int id) {
    try {
      String query = "UPDATE wish SET reserved = ? WHERE wish_id = ?";

      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);

      ps.setBoolean(1, isReserved);
      ps.setInt(2, id);
      ps.execute();
    } catch (SQLException e) {
      System.out.println(e.getMessage()); // TODO
    }
  }

  @Override
  public void editWish(String name, String link, String price, int id) {
    try {
      String query = "UPDATE wish SET name = ?, link = ?, price = ? WHERE wish_id = ?";

      PreparedStatement preparedStatement =
          DBManager.getInstance().getConnection().prepareStatement(query);
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, link);
      preparedStatement.setString(3, price);
      preparedStatement.setInt(4, id);
      preparedStatement.executeUpdate();

    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage()); // TODO
    }
  }

  @Override
  public void addWishToWishList(Wish wish) {
    try {
      String query =
          "INSERT INTO wish(name, link, price, wishlist_id, reserved) VALUES (?, ?, ?, ?, ?)";

      PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(query);
      ps.setString(1, wish.getName());
      ps.setString(2, wish.getLink());
      ps.setString(3, wish.getPrice());
      ps.setInt(4, wish.getWishListId());
      ps.setBoolean(5, wish.isReserved());

      ps.execute();
    } catch (SQLException sqlException) {
      System.out.println(sqlException.getMessage()); // TODO
    }
  }
}
