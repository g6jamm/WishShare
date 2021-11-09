package com.group6.wishshare.domain.model;

import java.util.List;

public class Wishlist {
  private final int ID;
  private final String NAME;
  private final User USER;
  private final String TOKEN;
  private final List<Wish> WISH_LIST;

  public Wishlist(WishListBuilder builder) {
    ID = builder.id;
    NAME = builder.name;
    USER = builder.user;
    TOKEN = builder.token;
    WISH_LIST = builder.wishList;
  }

  public List<Wish> getWishlist() {
    return WISH_LIST;
  }

  public int getId() {
    return ID;
  }

  public String getName() {
    return NAME;
  }

  public User getUser() {
    return USER;
  }

  public String getToken() {
    return TOKEN;
  }

  public static class WishListBuilder {
    private int id;
    private String name;
    private User user;
    private String token;
    private List<Wish> wishList;

    public WishListBuilder id(int id) {
      this.id = id;

      return this;
    }

    public WishListBuilder name(String name) {
      this.name = name;

      return this;
    }

    public WishListBuilder wishList(List<Wish> wishList) {
      this.wishList = wishList;

      return this;
    }

    public WishListBuilder user(User user) {
      this.user = user;

      return this;
    }

    public WishListBuilder token(String token) {
      this.token = token;

      return this;
    }

    private void reset() {
      id = 0;
      name = null;
      user = null;
      token = null;
    }

    public Wishlist build() {
      Wishlist result = new Wishlist(this);
      reset();
      return result;
    }
  }
}
