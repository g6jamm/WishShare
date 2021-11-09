package com.group6.wishshare.domain.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptService {
  public static String encrypt(String text, String salt) {
    try {
      return Arrays.toString(getSHA512(text + salt));
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  private static byte[] getSHA512(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-512");

    return md.digest(input.getBytes(StandardCharsets.UTF_8));
  }
}
