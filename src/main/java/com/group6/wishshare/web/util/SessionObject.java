package com.group6.wishshare.web.util;

import com.group6.wishshare.domain.model.User;
import org.springframework.web.context.request.WebRequest;

public class SessionObject {
  WebRequest webRequest;

  public SessionObject(WebRequest webRequest) {
    this.webRequest = webRequest;
  }

  public Object getUser() {
    return webRequest.getAttribute("user", webRequest.SCOPE_SESSION);
  }

  public void setUser(User user) {
    webRequest.setAttribute("user", user.getId(), WebRequest.SCOPE_SESSION);
  }

  public void removeUser() {
    webRequest.removeAttribute("user", webRequest.SCOPE_SESSION);
  }
}
