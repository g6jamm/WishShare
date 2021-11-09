package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;

public class WishListService {

  public Wishlist addWishList(User user, String name) {
    WishListRepository wishListRepository = new WishListRepository();
    // telemetry goes here
    // How other gender makes new wishlists
    return wishListRepository.addWishList(name, user);
  }

  public Wishlist lookupWishListById(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlistById(id);
  }

  public Wishlist lookupWishListByToken(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlistById(id);
  }

  public List<Wishlist> lookupWishListsPrUser(User user) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishLists(user);
  }

  public boolean isListOwner(int wishlistId, User user) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.isListOwnedByUser(wishlistId, user);
  }

  public void updateWishListName(int wishlistId, String newName) {
    WishListRepository wishListRepository = new WishListRepository();
    wishListRepository.updateName(wishlistId, newName);
  }

  public boolean deleteWishlist(int wishlistId) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.deleteWishlist(wishlistId);
  }
}
