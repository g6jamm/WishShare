package com.group6.wishshare.web;

import com.group6.wishshare.data.repository.WishRepository;
import com.group6.wishshare.domain.model.Wish;
import com.group6.wishshare.domain.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class WishController {

  WishService wishService = new WishService(new WishRepository());

  @GetMapping("/create-wish")
  public String createwish(Model model) {
    List<Wish> wishes = wishService.getWishes(1);
    model.addAttribute("wishes", wishes);
    return "createwish";
  }

  @PostMapping("/new-wish")
  public String newWish(WebRequest webRequest) {
    String name = webRequest.getParameter("wish");
    String link = webRequest.getParameter("link");
    String price = webRequest.getParameter("price");

    wishService.createWish(name, link, price, 1);

    return "redirect:/create-wish";
  }
}
