package com.group6.wishshare.domain.model;

import com.group6.wishshare.domain.model.type.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/** @auther Mathias */
class UserBuilderTest {
  static User user =
      new User.UserBuilder()
          .id(1)
          .firstName("Maggy")
          .lastName("Briggs")
          .email("lorem.fringilla.ornare@arcu.edu")
          .birthdate(LocalDate.of(1992, 10, 9))
          .gender(Gender.FEMALE)
          .build();

  @Test
  void testId() {
    Assertions.assertEquals(1, user.getId());
  }

  @Test
  void testFirstName() {
    Assertions.assertEquals("Maggy", user.getFirstName());
  }

  @Test
  void testLastName() {
    Assertions.assertEquals("Briggs", user.getLastName());
  }

  @Test
  void testEmail() {
    Assertions.assertEquals("lorem.fringilla.ornare@arcu.edu", user.getEmail());
  }

  @Test
  void testBirthdate() {
    Assertions.assertEquals(LocalDate.parse("1992-10-09"), user.getBirthdate());
  }

  @Test
  void testGender() {
    Assertions.assertEquals(Gender.FEMALE, user.getGender());
  }
}
