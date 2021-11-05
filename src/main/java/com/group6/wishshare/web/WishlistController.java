package com.group6.wishshare.web;

import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class WishlistController {

  @GetMapping("/dashboard")
  public String dashboard(WebRequest webRequest, Model model) {
    if (validateUser(webRequest)) {
      WishListService wishListService = new WishListService();
      model.addAttribute(
          "wishlists",
          wishListService.lookupWishListsPrUser(
              (User) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION)));

      return "/dashboard";
    }
    return "redirect:/login";
  }

  @PostMapping("/create-new-wish-list")
  public String createNewWishList(WebRequest webRequest) {
    if (validateUser(webRequest)) {
      WishListService wishListService = new WishListService();
      int id =
          wishListService.addWishList(
              (User) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION),
              webRequest.getParameter("wishlistname"));
      if (id != 0) {
        return "redirect:/wishlist/" + id;
      }
      return "redirect:/dashboard";
    }
    return "redirect:/login";
  }

  @PostMapping("/update-wishlist/{id}")
  public String updateWishlistName(WebRequest webRequest, @PathVariable int id) {
    // add owner check
    if (validateUser(webRequest)) {
      String newName = webRequest.getParameter("newName");
      WishListService wishListService = new WishListService();
      wishListService.updateWishListName(id, newName);
    }
    return "redirect:/dashboard"; // swap to wishpage maybe?
  }

  private boolean validateUser(WebRequest request) {
    User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

    return null != user;
  }
}
