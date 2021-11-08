package com.group6.wishshare.domain.model;

import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;
import java.util.List;

/**
 * Create User object using Builder pattern.
 *
 * @auther Mathias
 */
public class User {
  private final int ID;
  private final String FIRST_NAME;
  private final String LAST_NAME;
  private final String EMAIL;
  private final LocalDate BIRTHDATE;
  private final Gender GENDER;
  private final String PASSWORD;
  private final List<Wishlist> WISHLISTS;

  private User(UserBuilder builder) {
    this.ID = builder.id;
    this.FIRST_NAME = builder.firstName;
    this.LAST_NAME = builder.lastName;
    this.EMAIL = builder.email;
    this.BIRTHDATE = builder.birthdate;
    this.GENDER = builder.gender;
    this.PASSWORD = builder.password;
    this.WISHLISTS = builder.wishlists;
  }

  public int getId() {
    return ID;
  }

  public String getFirstName() {
    return FIRST_NAME;
  }

  public String getLastName() {
    return LAST_NAME;
  }

  public String getEmail() {
    return EMAIL;
  }

  public LocalDate getBirthdate() {
    return BIRTHDATE;
  }

  public Gender getGender() {
    return GENDER;
  }

  public String getPassword() {
    return PASSWORD;
  }

  public List<Wishlist> getWISHLISTS() {
    return WISHLISTS;
  }

  /**
   * Build User object.
   *
   * @auther Mathias
   */
  public static class UserBuilder {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Gender gender;
    private String password;
    private List<Wishlist> wishlists;

    public UserBuilder() {}

    public UserBuilder id(int id) {
      this.id = id;

      return this;
    }

    public UserBuilder firstName(String firstName) {
      this.firstName = firstName;

      return this;
    }

    public UserBuilder lastName(String lastName) {
      this.lastName = lastName;

      return this;
    }

    public UserBuilder email(String email) {
      this.email = email;

      return this;
    }

    public UserBuilder birthdate(LocalDate birthdate) {
      this.birthdate = birthdate;

      return this;
    }

    public UserBuilder gender(Gender gender) {
      this.gender = gender;

      return this;
    }

    public UserBuilder wishlists(List<Wishlist> wishlists){
      this.wishlists = wishlists;

      return this;
    }

    public UserBuilder password(String password) {
      this.password = password;

      return this;
    }

    public User build() {
      // Todo add reset? Add interface for reset and build.
      return new User(this);
    }
  }
}
