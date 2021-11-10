package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public interface WishRepository {

  List<Wish> getWishes(int id);

  Wish getWish(int id);

  void isReservedWish(boolean isReserved, int id);

  void editWish(String name, String link, String price, int id);

  void addWishToWishList(Wish wish);

  boolean deleteWish(int id);

}
