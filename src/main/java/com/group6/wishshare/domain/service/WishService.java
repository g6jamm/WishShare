package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.Wish;

import java.util.List;

public class WishService {

  WishRepository wishRepository;

  public WishService(WishRepository wishRepository) {
    this.wishRepository = wishRepository;
  }

  public void createWish(String name, String link, String price, int wishlistId) {
    Wish wish =
        new Wish.WishBuilder().name(name).link(link).price(price).wishlistId(wishlistId).build();

    wishRepository.addWishToWishList(wish);
  }

  public List<Wish> getWishes(int wishlistId) {
    List<Wish> result = wishRepository.getWishes(wishlistId);
    return result;
  }

  public boolean reserveWish(int wishId) {
    return wishRepository.reserveWish(wishId);
  }

}
