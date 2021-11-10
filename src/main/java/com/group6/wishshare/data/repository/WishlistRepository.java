package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;

public interface WishlistRepository {

  Wishlist addWishlist(String name, User user, String token);

  boolean isWishlistOwnedByUser(int id, User user);

  Wishlist findWishlistById(int id);

  Wishlist findWishlistByToken(String token);

  List<Wishlist> getWishlists(User user);

  void updateName(int id, String name);

  void deleteWishlist(int id);

  boolean deleteWishes(int id);

}

