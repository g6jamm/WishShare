package com.group6.wishshare.web;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wish;
import com.group6.wishshare.domain.model.Wishlist;
import com.group6.wishshare.domain.service.LoginService;
import com.group6.wishshare.domain.service.UserService;
import com.group6.wishshare.domain.service.WishListService;
import com.group6.wishshare.domain.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class WishController {

  WishService wishService = new WishService(new WishRepository());
  WishListService wishListService = new WishListService();
  LoginService loginService = new LoginService(new DataFacade());
  UserService userService = new UserService();

  @GetMapping("/create-wish")
  public String createWish(Model model) {
    List<Wish> wishes = wishService.getWishes(1); // TODO spørg tine omkring det
    model.addAttribute("wishes", wishes);
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
    Wishlist wishlist = wishListService.lookupWishListById(id); // // TODO spørg tine omkring det
    model.addAttribute("wishes", wishlist.getWishlist());
    model.addAttribute("wishlist_id", wishlist.getId());
    if (isValidUser(webRequest)) {
      User user =
          userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
      if (wishListService.isListOwner(id, user)) {
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

    if (isValidUser(webRequest)) {
      User user =
          userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));

      if (wishListService.isListOwner(wishlist.getId(), user)) { // TODO: move to method
        return "index"; // TODO: You are not allowed to see your own list - page ..
      }
    }

    return "shared-wishlist";
  }

  @PostMapping("/shared-wishlist/{token}/reserve/{wish_id}")
  public String reserve(@PathVariable String token, @PathVariable int wish_id, Model model) {
    Wish wish = wishService.getWish(wish_id);
    if (wish.isReserved()) {
      wishService.reserveWish(false, wish_id);
    } else {
      wishService.reserveWish(true, wish_id);
    }
    model.addAttribute("wish", wish);
    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/shared-wishlist/{token}/unreserve/{wish_id}")
  public String unreserve(@PathVariable String token, @PathVariable int wish_id) {

    wishService.reserveWish(false, wish_id);
    return "redirect:/shared-wishlist/" + token;
  }

  @PostMapping("/wishlist/{wishlist_id}/edit/{wish_id}")
  public String editWish(@PathVariable int wish_id, @PathVariable int wishlist_id, Model model) {
    Wish wish = wishService.getWish(wish_id);
    model.addAttribute("wishlist_id", wishlist_id);
    model.addAttribute("wish", wish);
    return "editwish";
  }

  @PostMapping("/wishlist/{wishlist_id}/save/{wish_id}")
  public String saveChanges(
      WebRequest webRequest, @PathVariable int wishlist_id, @PathVariable int wish_id) {
    String name = webRequest.getParameter("name");
    String link = webRequest.getParameter("link");
    String price = webRequest.getParameter("price");
    wishService.editWish(name, link, price, wish_id);

    return "redirect:/wishlist/" + wishlist_id;
  }

  private boolean isValidUser(WebRequest request) {
    Integer user_id = (Integer) request.getAttribute("user", WebRequest.SCOPE_SESSION);
    if (user_id == null) {
      return false;
    }
    return loginService.userExist(user_id);
  }
}
