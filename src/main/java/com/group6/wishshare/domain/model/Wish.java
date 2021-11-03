package com.group6.wishshare.domain.model;

/**
 *
 * @author Jackie, Mohamad
 */


public class Wish {

  private final int ID;
  private final String NAME;
  private final String LINK;
  private final String PRICE;
  private final int WISHLIST_ID;
  private final boolean RESERVED;

  private Wish(WishBuilder wishBuilder){
    this.ID = wishBuilder.id;
    this.NAME = wishBuilder.name;
    this.LINK = wishBuilder.link;
    this.PRICE = wishBuilder.price;
    this.WISHLIST_ID = wishBuilder.wishlistId;
    this.RESERVED = wishBuilder.reserved;
  }

  public int getID() {
    return ID;
  }

  public String getNAME() {
    return NAME;
  }

  public String getLINK() {
    return LINK;
  }

  public String getPRICE() {
    return PRICE;
  }

  public int getWISHLIST_ID() {
    return WISHLIST_ID;
  }

  public boolean isRESERVED() {
    return RESERVED;
  }

  public static class WishBuilder{

    private int id;
    private String name;
    private String link;
    private String price;
    private int wishlistId;
    private boolean reserved;

    public WishBuilder(){

    }

    public WishBuilder id(int id){
      this.id = id;
      return this;
    }

    public WishBuilder name(String name){
      this.name = name;
      return this;
    }

    public WishBuilder link(String link){
      this.link = link;
      return this;
    }

    public WishBuilder price(String price){
      this.price = price;
      return this;
    }

    public WishBuilder  wishlistId(int wishlistId){
      this.wishlistId = wishlistId;
      return this;
    }

    public WishBuilder reserved(boolean reserved){
      this.reserved = reserved;
      return this;
    }

    public Wish build(){
      return new Wish(this);
    }
  }

}
