package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishRepositoryImpl;
import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public class WishService {

  WishRepositoryImpl wishRepositoryImpl;

  public WishService(WishRepositoryImpl wishRepositoryImpl) {
    this.wishRepositoryImpl = wishRepositoryImpl;
  }

  public void createWish(String name, String link, String price, int id) {
    Wish wish = new Wish.WishBuilder().name(name).link(link).price(price).wishlistId(id).build();

    wishRepositoryImpl.addWishToWishList(wish);
  }

  public List<Wish> getWishes(int id) {
    return wishRepositoryImpl.getWishes(id);
  }

  public Wish getWish(int id) {
    return wishRepositoryImpl.getWish(id);
  }

  public void reserveWish(boolean isReserved, int id) {
    wishRepositoryImpl.isReservedWish(isReserved, id);
  }

  public void editWish(String name, String link, String price, int id) {
    wishRepositoryImpl.editWish(name, link, price, id);
  }
}
