package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishlistRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;
import java.util.Random;

public class WishlistService {

  private final WishlistRepository WISHLIST_REPOSITORY;

  public WishlistService(WishlistRepository WISHLIST_REPOSITORY) {
    this.WISHLIST_REPOSITORY = WISHLIST_REPOSITORY;
  }

  private String getRandomString() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;

    return new Random()
        .ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public Wishlist addWishList(User user, String name) {
    return WISHLIST_REPOSITORY.addWishlist(name, user, getRandomString());
  }

  public Wishlist getWishlistById(int id) {
    return WISHLIST_REPOSITORY.findWishlistById(id);
  }

  public Wishlist findWishListByToken(String token) {
    return WISHLIST_REPOSITORY.findWishlistByToken(token);
  }

  public List<Wishlist> getWishlists(User user) {
    return WISHLIST_REPOSITORY.getWishlists(user);
  }

  public boolean isOwner(int id, User user) {
    return WISHLIST_REPOSITORY.isWishlistOwnedByUser(id, user);
  }

  public void updateWishlistName(int id, String newName) {
    WISHLIST_REPOSITORY.updateName(id, newName);
  }

  public void deleteWishlist(int id) {
    WISHLIST_REPOSITORY.deleteWishlist(id);
  }
}
