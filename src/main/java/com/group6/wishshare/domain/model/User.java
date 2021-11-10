package com.group6.wishshare.domain.model;

import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;

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

  private User(UserBuilder builder) {
    this.ID = builder.id;
    this.FIRST_NAME = builder.firstName;
    this.LAST_NAME = builder.lastName;
    this.EMAIL = builder.email;
    this.BIRTHDATE = builder.birthdate;
    this.GENDER = builder.gender;
    this.PASSWORD = builder.password;
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

  /**
   * Build User object.
   *
   * @auther Mathias
   */
  public static class UserBuilder implements BuilderInterface {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Gender gender;
    private String password;

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

    public UserBuilder password(String password) {
      this.password = password;
      return this;
    }

    private void reset() {
      id = 0;
      firstName = null;
      lastName = null;
      email = null;
      birthdate = LocalDate.parse("2021-01-01");
      gender = Gender.OTHER;
      password = null;
    }

    public User build() {
      User result = new User(this);
      reset();
      return result;
    }
  }
}
