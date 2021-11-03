package com.group6.wishshare.domain.model;

public class Wishlist {
  private final int ID;
  private final String NAME;
  private final int USER_ID;

  public Wishlist(WishListBuilder builder) {
    ID = builder.id;
    NAME = builder.name;
    USER_ID = builder.userId;
  }

  public int getId() {
    return ID;
  }

  public String getName() {
    return NAME;
  }

  public int getUserId() {
    return USER_ID;
  }

  public static class WishListBuilder {
    private int id;
    private String name;
    private int userId;

    public WishListBuilder id(int id) {
      this.id = id;

      return this;
    }

    public WishListBuilder name(String name) {
      this.name = name;

      return this;
    }

    public WishListBuilder userid(int userId) {
      this.userId = userId;

      return this;
    }

    private void reset() {
      id = 0;
      name = null;
      userId = 0;
    }

    public Wishlist build() {
      Wishlist result = new Wishlist(this);
      reset();
      return result;
    }
  }
}
