package com.group6.wishshare.web.controller;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;
import com.group6.wishshare.domain.service.LoginException;
import com.group6.wishshare.domain.service.LoginService;
import com.group6.wishshare.web.util.SessionObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.Objects;

@Controller
public class LoginController {
  private final LoginService LOGIN_CONTROLLER = new LoginService(new DataFacade());

  @GetMapping("/")
  public String getIndex() {
    return "index";
  }

  @GetMapping("/login")
  public String getLogin() {
    return "login";
  }

  @GetMapping("/signup")
  public String getSignup() {
    return "signup";
  }

  @PostMapping("/login")
  public String loginUser(WebRequest webRequest, Model model) {
    try {
      String email = webRequest.getParameter("email");
      String password = webRequest.getParameter("password");

      User user = LOGIN_CONTROLLER.login(email, password);
      new SessionObject(webRequest).setUser(user);
      return "redirect:/dashboard";
    } catch (LoginException e) {
      model.addAttribute("loginFail", "Wrong email or password"); // TODO
      return "login";
    }
  }

  @PostMapping("/signup")
  public String createUser(WebRequest webRequest, Model model) {
    String firstname = webRequest.getParameter("firstname");
    String lastname = webRequest.getParameter("lastname");
    String email = webRequest.getParameter("email");
    String birthdate = webRequest.getParameter("birthdate");
    String gender = webRequest.getParameter("gender");
    String password1 = webRequest.getParameter("password1");
    String password2 = webRequest.getParameter("password2");

    try {
      if (validatePassword(password1, password2)) {
        User user =
            LOGIN_CONTROLLER.createUser(
                firstname,
                lastname,
                LocalDate.parse(Objects.requireNonNull(birthdate)),
                Gender.valueOf(gender),
                email,
                password1);
        new SessionObject(webRequest).setUser(user);
        return "redirect:/dashboard";
      }
      model.addAttribute("signupFail", "The passwords did not match"); // TODO
      return "signup";
    } catch (LoginException e) {
      model.addAttribute("signupFail", "User already exist"); // TODO
      return "signup";
    }
  }

  @GetMapping("/logout")
  public String logout(WebRequest webRequest) {
    new SessionObject(webRequest).removeUser();
    return "redirect:/";
  }

  private boolean validatePassword(String password1, String password2) { // TODO move
    return Objects.requireNonNull(password1).equals(password2);
  }

  @ExceptionHandler(Exception.class)
  public String error(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "error";
  }
}
