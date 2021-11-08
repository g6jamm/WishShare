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

@Controller
public class WishlistController {

  LoginService loginService = new LoginService(new DataFacade());
  UserService userService = new UserService();

  @GetMapping("/dashboard")
  public String dashboard(WebRequest webRequest, Model model) {
    if (validateUser(webRequest)) {
      WishListService wishListService = new WishListService();
      model.addAttribute(
          "wishlists",
          wishListService.lookupWishListsPrUser(
              (Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION)));

      return "/dashboard";
    }
    return "redirect:/login";
  }

  @PostMapping("/create-new-wish-list")
  public String createNewWishList(WebRequest webRequest) {
    if (validateUser(webRequest)) {
      WishListService wishListService = new WishListService();

      User user = userService.getUser((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION));
      Wishlist wishlist = wishListService.addWishList(user.getId(), webRequest.getParameter("wishlistname"));

      if (wishlist != null) {
        return "redirect:/wishlist/" + wishlist.getId();
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

  @PostMapping("/delete-wishlist/{id}")
  public String deleteWishList(WebRequest webRequest, @PathVariable int id) {
    WishListService wishListService = new WishListService();
    if (wishListService.isListOwner(
        id, ((Integer) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION)))) {
      wishListService.deleteWishlist(id);
    }
    return "redirect:/dashboard";
  }

  private boolean validateUser(WebRequest request) {
    Integer user_id = (Integer) request.getAttribute("user", WebRequest.SCOPE_SESSION);
    if(user_id == null){
      return false;
    }
    return loginService.userExist(user_id);
  }
}
