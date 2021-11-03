package com.group6.wishshare.data.repository;

import com.group6.wishshare.data.Util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishListRepository {

    Connection connection = DbManager.getInstance().getConnection();

    public boolean addWishList(String name, int userid){
        String stm = "INSERT INTO wishlist (name, user_id) VALUES (? , ?)";
        PreparedStatement prep;

        try{
            prep = connection.prepareStatement(stm);
            prep.setString(1,name);
            prep.setInt(2, userid);

            boolean result = prep.execute();

            return  result;

        } catch (SQLException throwables) {
            return false;
        }

    }
}
