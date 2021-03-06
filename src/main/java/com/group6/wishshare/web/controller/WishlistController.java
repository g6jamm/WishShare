package com.group6.wishshare.web.controller;

import com.group6.wishshare.data.repository.mysql.UserRepositoryImpl;
import com.group6.wishshare.data.repository.mysql.WishRepositoryImpl;
import com.group6.wishshare.data.repository.mysql.WishlistRepositoryImpl;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wish;
import com.group6.wishshare.domain.model.Wishlist;
import com.group6.wishshare.domain.service.UserService;
import com.group6.wishshare.domain.service.WishService;
import com.group6.wishshare.domain.service.WishlistService;
import com.group6.wishshare.web.util.SessionObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class WishlistController {

  private final WishService WISH_SERVICE = new WishService(new WishRepositoryImpl());
  private final WishlistService WISHLIST_SERVICE =
      new WishlistService(new WishlistRepositoryImpl());
  private final UserService USER_SERVICE = new UserService(new UserRepositoryImpl());

  @PostMapping("/wishlist/{id}/new-wish")
  public String newWish(WebRequest webRequest, @PathVariable int id) {
    String name = webRequest.getParameter("wish");
    String link = webRequest.getParameter("link");
    String price = webRequest.getParameter("price");

    WISH_SERVICE.createWish(name, link, price, id);

    return "redirect:/wishlist/" + id;
  }

  @GetMapping("/wishlist/{id}")
  public String wishlist(WebRequest webRequest, @PathVariable int id, Model model) {
    Wishlist wishlist = WISHLIST_SERVICE.getWishlistById(id);
    model.addAttribute("wishlist", wishlist);
    model.addAttribute("wishlist_id", wishlist.getId());

    Object userSession = new SessionObject(webRequest).getUser();
    if (USER_SERVICE.isValidUser(userSession)) {
      if (WISHLIST_SERVICE.isOwner(id, USER_SERVICE.getUser(userSession))) {
        return "createwish";
      }
      return "dashboard";
    }

    return "index"; // TODO: code 404
  }

  @GetMapping("/shared-wishlist/{token}")
  public String sharedWishlist(WebRequest webRequest, @PathVariable String token, Model model) {
    Wishlist wishlist = WISHLIST_SERVICE.findWishListByToken(token);
    model.addAttribute("wishlist", wishlist);
    model.addAttribute("wishlist_id", wishlist.getId());

    Object userSession = new SessionObject(webRequest).getUser();
    if (USER_SERVICE.isValidUser(userSession)) {
      if (WISHLIST_SERVICE.isOwner(wishlist.getId(), USER_SERVICE.getUser(userSession))) {
        return "redirect:/wishlist/" + wishlist.getId();
      }
    }

    return "shared-wishlist";
  }

  @PostMapping("/shared-wishlist/{token}/reserve/{id}")
  public String reserve(@PathVariable String token, @PathVariable int id, Model model) {
    Wish wish = WISH_SERVICE.getWish(id);
    WISH_SERVICE.reserveWish(!wish.isReserved(), id);
    model.addAttribute("wish", wish);

    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/shared-wishlist/{token}/unreserve/{id}")
  public String unReserve(@PathVariable String token, @PathVariable int id) {
    WISH_SERVICE.reserveWish(false, id);

    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/wishlist/{wishlistId}/delete/{wishId}")
  public String deleteWish(@PathVariable int wishlistId, @PathVariable int wishId, Model model) {
    Wish wish = WISH_SERVICE.getWish(wishId);
    model.addAttribute("wishlist_id", wishlistId);
    model.addAttribute("wish", wish);
    WISH_SERVICE.deleteWish(wishId);
    return "redirect:/wishlist/" + wishlistId;
  }

  @PostMapping("/wishlist/{wishlistId}/edit/{wishId}")
  public String editWish(@PathVariable int wishId, @PathVariable int wishlistId, Model model) {
    Wish wish = WISH_SERVICE.getWish(wishId);
    model.addAttribute("wishlist_id", wishlistId);
    model.addAttribute("wish", wish);

    return "editwish";
  }

  @PostMapping("/wishlist/{wishlistId}/save/{wishId}")
  public String saveChanges(
      WebRequest webRequest, @PathVariable int wishlistId, @PathVariable int wishId) {
    String name = webRequest.getParameter("name");
    String link = webRequest.getParameter("link");
    String price = webRequest.getParameter("price");
    WISH_SERVICE.editWish(name, link, price, wishId);

    return "redirect:/wishlist/" + wishlistId;
  }

  @PostMapping("/update-wishlist/{id}")
  public String updateWishlistName(WebRequest webRequest, @PathVariable int id, Model model) {
    Object userSession = new SessionObject(webRequest).getUser();
    model.addAttribute("Saved", "Name has been updated");

    if (USER_SERVICE.isValidUser(userSession)) {
      User user = USER_SERVICE.getUser(userSession);
      if (WISHLIST_SERVICE.isOwner(id, user)) {
        String newName = webRequest.getParameter("newName");
        WISHLIST_SERVICE.updateWishlistName(id, newName);
      }
    }
    return wishlist(webRequest, id, model);
  }
}
