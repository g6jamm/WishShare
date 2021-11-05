package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;

public class WishListService {

  public int addWishList(User user, String name) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.addWishList(name, user.getId());
  }

  public Wishlist lookupWishList(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlist(id);
  }

  public List<Wishlist> lookupWishListsPrUser(User user) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishLists(user.getId());
  }

  public boolean isListOwner(int wishlistId, int userId) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.isListOwnedByUser(wishlistId, userId);
  }

  public void updateWishListName(int wishlistId, String newName) {
    WishListRepository wishListRepository = new WishListRepository();
    wishListRepository.updateName(wishlistId, newName);
  }
}
