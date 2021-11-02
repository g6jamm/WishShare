package com.group6.wishshare.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/** @auther Mathias */
class UserBuilderTest {
  static LocalDate date = LocalDate.of(1992, 10, 9);

  @Test
  void testFirstName() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals("Maggy", user.getFirstName());
  }

  @Test
  void testLastName() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals("Briggs", user.getLastName());
  }

  @Test
  void testEmail() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals("lorem.fringilla.ornare@arcu.edu", user.getEmail());
  }

  @Test
  void testBirthdate() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals(LocalDate.parse("1992-10-09"), user.getBirthdate());
  }

  @Test
  void testGender() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals(Gender.FEMALE, user.getGender());
  }

  @Test
  void testBuild() {
    User user =
        new User.UserBuilder()
            .firstname("Maggy")
            .lastName("Briggs")
            .email("lorem.fringilla.ornare@arcu.edu")
            .birthdate(date)
            .gender(Gender.FEMALE)
            .build();

    Assertions.assertEquals("Briggs", user.getLastName());
  }

  @Test
  void testBuildMissingArgument() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () ->
            new User.UserBuilder()
                .firstname("Maggy")
                .email("lorem.fringilla.ornare@arcu.edu")
                .birthdate(date)
                .gender(Gender.FEMALE)
                .build());
  }
}
