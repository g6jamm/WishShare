package com.group6.wishshare.web.controller;

import com.group6.wishshare.data.repository.WishlistRepositoryImpl;
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
public class WishlistController {

  UserService userService = new UserService();
  WishlistService wishlistService = new WishlistService(new WishlistRepositoryImpl());

  @GetMapping("/dashboard")
  public String dashboard(WebRequest webRequest, Model model) {
    Object userSession = new SessionObject(webRequest).getUser();

    if (userService.isValidUser(userSession)) {
      User user = userService.getUser(userSession);
      List<Wishlist> wishlistList = wishlistService.getWishlists(user);

      model.addAttribute("wishlists", wishlistList);

      return "/dashboard";
    }

    return "redirect:/login";
  }

  @PostMapping("/create-new-wish-list")
  public String createNewWishList(WebRequest webRequest) {
    Object userSession = new SessionObject(webRequest).getUser();

    if (userService.isValidUser(userSession)) {

      User user = userService.getUser(userSession);
      Wishlist wishlist =
          wishlistService.addWishList(user, webRequest.getParameter("wishlistname"));

      if (null != wishlist) {
        return "redirect:/wishlist/" + wishlist.getId();
      }
      return "redirect:/dashboard";
    }

    return "redirect:/login";
  }

  @PostMapping("/update-wishlist/{id}")
  public String updateWishlistName(WebRequest webRequest, @PathVariable int id) {
    Object userSession = new SessionObject(webRequest).getUser();

    if (userService.isValidUser(userSession)) {
      User user = userService.getUser(userSession);
      if (wishlistService.isOwner(id, user)) {
        String newName = webRequest.getParameter("newName");
        wishlistService.updateWishlistName(id, newName);
      }
    }

    return "redirect:/dashboard"; // TODO swap to wishpage maybe?
  }

  @PostMapping("/delete-wishlist/{id}")
  public String deleteWishList(WebRequest webRequest, @PathVariable int id) {
    Object userSession = new SessionObject(webRequest).getUser();
    User user = userService.getUser(userSession);
    if (wishlistService.isOwner(id, user)) {
      wishlistService.deleteWishlist(id);
    }

    return "redirect:/dashboard";
  }
}
