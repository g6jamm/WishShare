package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.Wishlist;

import java.util.List;

public class WishListService {

  public Wishlist addWishList(int user_id, String name) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.addWishList(name, user_id);
  }

  public Wishlist lookupWishListById(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlistById(id);
  }

  public Wishlist lookupWishListByToken(int id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishlistById(id);
  }

  public List<Wishlist> lookupWishListsPrUser(int user_id) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.getWishLists(user_id);
  }

  public boolean isListOwner(int wishlistId, int userId) {
    WishListRepository wishListRepository = new WishListRepository();
    return wishListRepository.isListOwnedByUser(wishlistId, userId);
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
