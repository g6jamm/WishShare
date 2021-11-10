package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public class WishRepositoryStub implements WishRepository {
  @Override
  public List<Wish> getWishes(int id) {
    return null;
  }

  @Override
  public Wish getWish(int id) {
    return null;
  }

  @Override
  public void isReservedWish(boolean isReserved, int id) {}

  @Override
  public void editWish(String name, String link, String price, int id) {}

  @Override
  public void addWishToWishList(Wish wish) {}

  @Override
  public boolean deleteWish(int id) {
    return false;
  }

}
