package com.group6.wishshare.domain.service;

import com.group6.wishshare.data.repository.WishListRepository;
import com.group6.wishshare.domain.model.User;

public class WishListService {

    public boolean addWishList(User user, String name){
        WishListRepository wishListRepository = new WishListRepository();
        return wishListRepository.addWishList(name, user.getId());
    }
}
