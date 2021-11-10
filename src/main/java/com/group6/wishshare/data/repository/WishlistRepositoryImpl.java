package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.util.DBManager;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistRepositoryImpl implements WishlistRepository {

  Connection connection = DBManager.getInstance().getConnection();
  WishRepositoryImpl wishRepositoryImpl = new WishRepositoryImpl();
  UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();

  /**
   * Method to add a wishlist to database. @Returns if successful return a new wishlist else null.
   *
   * @auther Andreas
   */
  @Override
  public Wishlist addWishlist(String name, User user, String token) {
    try {
      String query = "INSERT INTO wishlist (name, token, user_id) VALUES (?, ?, ?)";

      PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, name);
      ps.setString(2, token);
      ps.setInt(3, user.getId());
      ps.executeUpdate();

      ResultSet gk = ps.getGeneratedKeys();

      if (gk.next()) {
        return new Wishlist.WishListBuilder()
            .wishList(new ArrayList<>())
            .name(name)
            .id(gk.getInt(1))
            .token(token)
            .user(user)
            .build();
      }
    } catch (SQLException e) {
      System.out.println("WishlistRepository: " + e.getMessage()); // TODO
    }
    return null;
  }

  /** @auther Jackie og Mohamad */
  @Override
  public boolean isWishlistOwnedByUser(int id, User user) {
    try {
      String query = "SELECT * FROM wishlist WHERE wishlist_id = ? AND user_id = ?;";
      PreparedStatement preparedStatement =
          DBManager.getInstance().getConnection().prepareStatement(query);
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, user.getId());

      ResultSet rs = preparedStatement.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      System.out.println(e.getMessage()); // TODO
    }

    return false;
  }

  /**
   * Method to retrieve a wishlist object from database using wishlist index. @Returns wishlist
   * object or null - I don't like this tho...
   *
   * @auther Andreas
   */
  @Override
  public Wishlist findWishlistById(int id) {
    try {
      String query = "SELECT * FROM wishlist WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new Wishlist.WishListBuilder()
            .id(rs.getInt("wishlist_id"))
            .name(rs.getString("name"))
            .user(userRepositoryImpl.getUser(rs.getInt("user_id")))
            .wishList(wishRepositoryImpl.getWishes(id))
            .build();
      }
    } catch (SQLException e) {
      // TODO
    }

    return null;
  }

  /**
   * Returns a wishlist by a token.
   *
   * @param token String
   * @return wishlist
   * @auther Mathias
   */
  @Override
  public Wishlist findWishlistByToken(String token) {
    try {
      String query = "SELECT * FROM wishlist WHERE token = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, token);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        int wishlistId = resultSet.getInt("wishlist_id");

        return new Wishlist.WishListBuilder()
            .id(wishlistId)
            .name(resultSet.getString("name"))
            .token(resultSet.getString("token"))
            .user(userRepositoryImpl.getUser(resultSet.getInt("user_id")))
            .wishList(wishRepositoryImpl.getWishes(wishlistId))
            .build();
      }
    } catch (SQLException e) {
      // TODO
    }
    return null;
  }

  @Override
  public List<Wishlist> getWishlists(User user) {
    List<Wishlist> result = new ArrayList<>();

    try {
      String query = "SELECT * FROM wishlist WHERE user_id = ?";
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, user.getId());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        result.add(
            new Wishlist.WishListBuilder()
                .id(rs.getInt("wishlist_id"))
                .name(rs.getString("name"))
                .token(rs.getString("token"))
                .user(user)
                .build());
      }
    } catch (SQLException e) {
      // TODO
    }
    return result;
  }

  @Override
  public void updateName(int id, String name) {
    try {
      String query = "UPDATE wishlist SET name = ? WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, name);
      ps.setInt(2, id);
      ps.executeUpdate();

    } catch (SQLException e) {
      // TODO
      System.out.println(e.getMessage());
    }
  }

  /** @param id id of wishlist to be deleted */
  @Override
  public void deleteWishlist(int id) {
    try {
      if (deleteWishes(id)) {
        String stm = "DELETE FROM wishlist WHERE wishlist_id = ?";
        PreparedStatement ps = connection.prepareStatement(stm);
        ps.setInt(1, id);
        ps.executeUpdate();
      }
    } catch (SQLException e) {
      // TODO
    }
  }

  /**
   * @param id id of wishlist to be deleted
   * @return True if successful, false if not.
   */
  @Override
  public boolean deleteWishes(int id) {
    try {
      String query = "DELETE FROM wish WHERE wishlist_id = ?";
      PreparedStatement ps = connection.prepareStatement(query);
      ps.setInt(1, id);
      ps.executeUpdate();
      return true;
    } catch (SQLException e) {
      return false;
    }
  }
}
