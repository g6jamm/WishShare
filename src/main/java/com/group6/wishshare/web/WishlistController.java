package com.group6.wishshare.web;

import com.group6.wishshare.data.repository.DataFacade;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wishlist;
import com.group6.wishshare.domain.service.LoginService;
import com.group6.wishshare.domain.service.UserService;
import com.group6.wishshare.domain.service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class WishlistController {

  LoginService loginService = new LoginService(new DataFacade());
  UserService userService = new UserService();

  @GetMapping("/dashboard")
  public String dashboard(WebRequest webRequest, Model model) {
    if (userService.isValidUser(
        (Integer) webRequest.getAttribute("user", webRequest.SCOPE_SESSION))) {
      WishListService wishListService = new WishListService();
      User user =
          userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
      List<Wishlist> wishlistList = wishListService.lookupWishListsPrUser(user);

      model.addAttribute("wishlists", wishlistList);

      return "/dashboard";
    }
    return "redirect:/login";
  }

  @PostMapping("/create-new-wish-list")
  public String createNewWishList(WebRequest webRequest) {
    if (userService.isValidUser(
        (Integer) webRequest.getAttribute("user", webRequest.SCOPE_SESSION))) {
      WishListService wishListService = new WishListService();

      User user =
          userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
      Wishlist wishlist =
          wishListService.addWishList(user, webRequest.getParameter("wishlistname"));

      if (wishlist != null) {
        return "redirect:/wishlist/" + wishlist.getId();
      }
      return "redirect:/dashboard";
    }
    return "redirect:/login";
  }

  @PostMapping("/update-wishlist/{id}")
  public String updateWishlistName(WebRequest webRequest, @PathVariable int id) {
    WishListService wishListService = new WishListService();
    // add owner check
    if (userService.isValidUser(
        (Integer) webRequest.getAttribute("user", webRequest.SCOPE_SESSION))) {
      User user =
          userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
      if (wishListService.isListOwner(id, user)) {
        String newName = webRequest.getParameter("newName");
        wishListService.updateWishListName(id, newName);
      }
    }
    return "redirect:/dashboard"; // swap to wishpage maybe?
  }

  @PostMapping("/delete-wishlist/{id}")
  public String deleteWishList(WebRequest webRequest, @PathVariable int id) {
    WishListService wishListService = new WishListService();
    User user =
        userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
    if (wishListService.isListOwner(id, user)) {
      wishListService.deleteWishlist(id);
    }
    return "redirect:/dashboard";
  }
}
