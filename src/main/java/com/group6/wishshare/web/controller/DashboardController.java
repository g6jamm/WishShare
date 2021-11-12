package com.group6.wishshare.web.controller;

import com.group6.wishshare.data.repository.mysql.UserRepositoryImpl;
import com.group6.wishshare.data.repository.mysql.WishlistRepositoryImpl;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;
import com.group6.wishshare.domain.service.UserService;
import com.group6.wishshare.domain.service.WishlistService;
import com.group6.wishshare.web.util.SessionObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class DashboardController {

  private final UserService USER_SERVICE = new UserService(new UserRepositoryImpl());
  private final WishlistService WISHLIST_SERVICE =
      new WishlistService(new WishlistRepositoryImpl());

  @GetMapping("/dashboard")
  public String dashboard(WebRequest webRequest, Model model) {
    Object userSession = new SessionObject(webRequest).getUser();

    if (USER_SERVICE.isValidUser(userSession)) {
      User user = USER_SERVICE.getUser(userSession);
      List<Wishlist> wishlistList = WISHLIST_SERVICE.getWishlists(user);

      model.addAttribute("wishlists", wishlistList);

      return "dashboard";
    }

    return "redirect:/login";
  }

  @PostMapping("/create-new-wish-list")
  public String createNewWishList(WebRequest webRequest) {
    Object userSession = new SessionObject(webRequest).getUser();

    if (USER_SERVICE.isValidUser(userSession)) {

      User user = USER_SERVICE.getUser(userSession);
      Wishlist wishlist =
          WISHLIST_SERVICE.addWishList(user, webRequest.getParameter("wishlistname"));

      if (null != wishlist) {
        return "redirect:/wishlist/" + wishlist.getId();
      }
      return "redirect:/dashboard";
    }

    return "redirect:/login";
  }

  @PostMapping("/delete-wishlist/{id}")
  public String deleteWishList(WebRequest webRequest, @PathVariable int id) {
    Object userSession = new SessionObject(webRequest).getUser();
    User user = USER_SERVICE.getUser(userSession);
    if (WISHLIST_SERVICE.isOwner(id, user)) {
      WISHLIST_SERVICE.deleteWishlist(id);
    }

    return "redirect:/dashboard";
  }
}
