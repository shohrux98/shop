package com.ecommers.repository;

import com.ecommers.models.User;
import com.ecommers.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);

    Optional<WishList> findByProductId(Integer id);
}
