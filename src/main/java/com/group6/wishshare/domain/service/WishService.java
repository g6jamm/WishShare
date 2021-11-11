package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public class WishService {

  private final WishRepository WISH_REPOSITORY;

  public WishService(WishRepository WISH_REPOSITORY) {
    this.WISH_REPOSITORY = WISH_REPOSITORY;
  }

  public void createWish(String name, String link, String price, int id) {
    Wish wish = new Wish.WishBuilder().name(name).link(link).price(price).wishlistId(id).build();

    WISH_REPOSITORY.addWishToWishList(wish);
  }

  public List<Wish> getWishes(int id) {
    return WISH_REPOSITORY.getWishes(id);
  }

  public Wish getWish(int id) {
    return WISH_REPOSITORY.getWish(id);
  }

  public void reserveWish(boolean isReserved, int id) {
    WISH_REPOSITORY.reserveWish(isReserved, id);
  }

  public void editWish(String name, String link, String price, int id) {
    WISH_REPOSITORY.editWish(name, link, price, id);
  }

  public void deleteWish(int id) {
    WISH_REPOSITORY.deleteWish(id);
  }
}
