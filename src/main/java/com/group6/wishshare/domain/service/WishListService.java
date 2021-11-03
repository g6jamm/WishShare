package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

public class WishListService {

  public boolean addWishList(User user, String name) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.addWishList(name, user.getId());
  }

  public Wishlist lookupWishList(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlist(id);
  }
}
