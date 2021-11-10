package com.group6.wishshare.web.controller;

import com.group6.wishshare.data.repository.UserRepositoryImpl;
import com.group6.wishshare.data.repository.WishRepositoryImpl;
import com.group6.wishshare.data.repository.WishlistRepositoryImpl;
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
public class WishController {

  WishService wishService = new WishService(new WishRepositoryImpl());
  WishlistService wishListService = new WishlistService(new WishlistRepositoryImpl());
  UserService userService = new UserService(new UserRepositoryImpl());

  @GetMapping("/create-wish")
  public String createWish(Model model) {
    model.addAttribute("wishes", wishService.getWishes(1));

    return "createwish";
  }

  @PostMapping("/wishlist/{id}/new-wish")
  public String newWish(WebRequest webRequest, @PathVariable int id) {
    String name = webRequest.getParameter("wish");
    String link = webRequest.getParameter("link");
    String price = webRequest.getParameter("price");

    wishService.createWish(name, link, price, id);

    return "redirect:/wishlist/" + id;
  }

  @GetMapping("/wishlist/{id}")
  public String wishlist(WebRequest webRequest, @PathVariable int id, Model model) {
    Wishlist wishlist = wishListService.getWishlistById(id);
    model.addAttribute("wishes", wishlist.getWishlist());
    model.addAttribute("wishlist_id", wishlist.getId());

    Object userSession = new SessionObject(webRequest).getUser();
    if (userService.isValidUser(userSession)) {
      if (wishListService.isOwner(id, userService.getUser(userSession))) {
        return "createwish";
      }
      return "dashboard";
    }

    return "index"; // TODO: code 404
  }

  @GetMapping("/shared-wishlist/{token}")
  public String sharedWishlist(WebRequest webRequest, @PathVariable String token, Model model) {
    Wishlist wishlist = wishListService.findWishListByToken(token);
    model.addAttribute("wishes", wishlist.getWishlist());
    model.addAttribute("wishlist_id", wishlist.getId());

    Object userSession = new SessionObject(webRequest).getUser();
    if (userService.isValidUser(userSession)) {
      if (wishListService.isOwner(wishlist.getId(), userService.getUser(userSession))) {
        return "index"; // TODO: You are not allowed to see your own list - page ..
      }
    }

    return "shared-wishlist";
  }

  @PostMapping("/shared-wishlist/{token}/reserve/{id}")
  public String reserve(@PathVariable String token, @PathVariable int id, Model model) {
    Wish wish = wishService.getWish(id);
    wishService.reserveWish(!wish.isReserved(), id);
    model.addAttribute("wish", wish);

    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/shared-wishlist/{token}/unreserve/{id}")
  public String unReserve(@PathVariable String token, @PathVariable int id) {
    wishService.reserveWish(false, id);

    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/wishlist/{wishlistId}/edit/{wishId}")
  public String editWish(@PathVariable int wishId, @PathVariable int wishlistId, Model model) {
    Wish wish = wishService.getWish(wishId);
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
    wishService.editWish(name, link, price, wishId);

    return "redirect:/wishlist/" + wishlistId;
  }
}
