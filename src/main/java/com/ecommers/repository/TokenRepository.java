package com.ecommers.repository;

import com.ecommers.models.AuthenticationToken;
import com.ecommers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
