package com.ecommers.service;

import com.ecommers.dto.ResponseDto;
import com.ecommers.models.WishList;
import com.ecommers.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    public ResponseDto delete(Integer id){
        Optional<WishList> byProductId = wishListRepository.findByProductId(id);
        if(byProductId.isEmpty()){
            return null;
        }
        wishListRepository.delete(byProductId.get());
        return new ResponseDto(true, "Delete");
    }
}
