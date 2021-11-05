package com.group6.wishshare.web;

import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.User;
import com.group6.wishshare.domain.model.Wish;
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

  @GetMapping("/create-wish")
  public String createwish(Model model) {
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
    List<Wish> wishes = wishService.getWishes(id); // // TODO spørg tine omkring det
    model.addAttribute("wishes", wishes);
    model.addAttribute("wishlist_id", id);
    if (validateUser(webRequest) && wishListService.isListOwner(id, ((User) webRequest.getAttribute("user", WebRequest.SCOPE_SESSION)).getId())) {
      return "createwish";
    }
    return "sharedwish";
  }

  @PostMapping("/wishlist/{wishlist_id}/reserve/{wish_id}")
  public String reserved(@PathVariable int wishlist_id, @PathVariable int wish_id) {
    wishService.reserveWish(wish_id);
    return "redirect:/wishlist/" + wishlist_id;
  }

  private boolean validateUser(WebRequest request) {
    User user = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);

    return null != user;
  }
}
