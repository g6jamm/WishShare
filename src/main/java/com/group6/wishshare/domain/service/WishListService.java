package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;
import java.util.Random;

public class WishListService {

  private String getRandomString() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    return random
        .ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public Wishlist addWishList(User user, String name) {
    WishListRepository wishListRepository = new WishListRepository();
    // telemetry goes here
    // How other gender makes new wishlists
    return wishListRepository.addWishList(name, user, getRandomString());
  }

  public Wishlist lookupWishListById(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlistById(id);
  }

  public Wishlist findWishListByToken(String token) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.findWishlistByToken(token);
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
