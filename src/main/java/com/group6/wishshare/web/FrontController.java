package com.group6.wishshare.web;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.type.Gender;
import com.group6.wishshare.domain.service.LoginException;
import com.group6.wishshare.domain.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.Objects;

@Controller
public class FrontController {
  private final LoginService loginController = new LoginService(new DataFacade());

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


  private boolean validateUser(WebRequest request) {
    User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

    return null != user;
  }

  @PostMapping("/login")
  public String loginUser(WebRequest request) throws LoginException {
    String email = request.getParameter("email");
    String pwd = request.getParameter("password");

    User user = loginController.login(email, pwd);
    setSessionInfo(request, user);

    return "redirect:/dashboard";
  }

  @PostMapping("/signup")
  public String createUser(WebRequest request) throws LoginException {
    // Retrieve values from HTML form via WebRequest
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");
    String email = request.getParameter("email");
    String birthdate = request.getParameter("birthdate");
    String gender = request.getParameter("gender");
    String password1 = request.getParameter("password1");
    String password2 = request.getParameter("password2");

    if (validatePassword(password1, password2)) {
      User user =
          loginController.createUser(
              firstname,
              lastname,
              LocalDate.parse(Objects.requireNonNull(birthdate)),
              Gender.valueOf(gender),
              email,
              password1);
      setSessionInfo(request, user);

      return "redirect:/dashboard";
    }

    throw new LoginException("The passwords did not match");
  }

  private boolean validatePassword(String password1, String password2) {
    return Objects.requireNonNull(password1).equals(password2);
  }

  private void setSessionInfo(WebRequest request, User user) {
    request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
  }

  @ExceptionHandler(Exception.class)
  public String anotherError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "error";
  }
}
