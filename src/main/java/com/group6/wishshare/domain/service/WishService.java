package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public class WishService {

  WishRepository wishRepository;

  public WishService(WishRepository wishRepository) {
    this.wishRepository = wishRepository;
  }

  public void createWish(String name, String link, String price, int id) {
    Wish wish = new Wish.WishBuilder().name(name).link(link).price(price).wishlistId(id).build();

    wishRepository.addWishToWishList(wish);
  }

  public List<Wish> getWishes(int id) {
    return wishRepository.getWishes(id);
  }

  public Wish getWish(int id) {
    return wishRepository.getWish(id);
  }

  public void reserveWish(boolean isReserved, int id) {
    wishRepository.isReservedWish(isReserved, id);
  }

  public void editWish(String name, String link, String price, int id) {
    wishRepository.editWish(name, link, price, id);
  }
}
