package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;
import com.group6.wishshare.domain.model.Wish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackie, Mohamad
 */

public class WishRepository {

  public List<Wish> getWishList(int wishlistId){
    List<Wish> result = new ArrayList<>();
    String sqlQuery = "SELECT * FROM wish WHERE wishlist_id = " + wishlistId;
    ResultSet resultSet = resultSet(sqlQuery);

    try{
      while (resultSet.next()){
        Wish wish = new Wish.WishBuilder().id(resultSet.getInt("wish_id")).
            name(resultSet.getString("name")).
            link(resultSet.getString("link")).
            price(resultSet.getString("price")).
            wishlistId(resultSet.getInt("wishlist_id")).
            reserved(resultSet.getBoolean("reserved")).build();
        result.add(wish);
      }
    }catch (SQLException sqlException){
      System.out.println(sqlException.getMessage());
    }
    return result;
  }

  public void addWishToWishList(Wish wish){

    String sqlQuery = "INSERT INTO wish(name, link, price, wishlist_id, reserved) VALUES(?,?,?,?,?)";

    PreparedStatement preparedStatement;

    try {
      preparedStatement = DbManager.getInstance().getConnection().prepareStatement(sqlQuery);
      preparedStatement.setString(1, wish.getNAME());
      preparedStatement.setString(2, wish.getLINK());
      preparedStatement.setString(3, wish.getPRICE());
      preparedStatement.setInt(4, wish.getWISHLIST_ID());
      preparedStatement.setBoolean(5, wish.isRESERVED());
      preparedStatement.executeUpdate();
    }catch (SQLException sqlException){
      System.out.println(sqlException.getMessage());
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
