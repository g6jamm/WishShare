package com.group6.wishshare.data.repository;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;

public class WishlistRepositoryStub implements WishlistRepository {
  @Override
  public Wishlist addWishlist(String name, User user, String token) {
    return null;
  }

  @Override
  public boolean isWishlistOwnedByUser(int id, User user) {
    return false;
  }

  @Override
  public Wishlist findWishlistById(int id) {
    return null;
  }

  @Override
  public Wishlist findWishlistByToken(String token) {
    return null;
  }

  @Override
  public List<Wishlist> getWishlists(User user) {
    return null;
  }

  @Override
  public void updateName(int id, String name) {}

  @Override
  public void deleteWishlist(int id) {}

  @Override
  public boolean deleteWishes(int id) {
    return false;
  }
}
