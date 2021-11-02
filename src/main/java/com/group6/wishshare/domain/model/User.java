package com.group6.wishshare.domain.model;

import com.group6.wishshare.domain.model.type.Gender;

import java.time.LocalDate;

/**
 * Create User object using Builder pattern.
 *
 * @auther Mathias
 */
public class User {
  private final String FIRST_NAME;
  private final String LAST_NAME;
  private final String EMAIL;
  private final LocalDate BIRTHDATE;
  private final Gender GENDER;

  private User(UserBuilder builder) {
    this.FIRST_NAME = builder.firstName;
    this.LAST_NAME = builder.lastName;
    this.EMAIL = builder.email;
    this.BIRTHDATE = builder.birthdate;
    this.GENDER = builder.gender;
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

  /**
   * Build User object.
   *
   * @auther Mathias
   */
  public static class UserBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthdate;
    private Gender gender;

    public UserBuilder() {}

    public UserBuilder firstName(String firstName) {
      if (firstName.length() > 255) {
        throw new IllegalArgumentException();
      }
      this.firstName = firstName;

      return this;
    }

    public UserBuilder lastName(String lastName) {
      if (lastName.length() > 255) {
        throw new IllegalArgumentException();
      }
      this.lastName = lastName;

      return this;
    }

    public UserBuilder email(String email) {
      if (email.length() > 255) {
        throw new IllegalArgumentException();
      }
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

    public User build() {
      User user = new User(this);
      validateUserObject();

      return user;
    }

    /**
     * Make suse that all values has been set.
     *
     * @auther Mathias
     */
    private void validateUserObject() {
      if (null == firstName
          || null == lastName
          || null == email
          || null == birthdate
          || null == gender) {
        throw new IllegalArgumentException("Missing parameter");
      }
    }
  }
}
