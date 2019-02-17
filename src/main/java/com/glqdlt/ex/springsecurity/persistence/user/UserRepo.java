package com.glqdlt.ex.springsecurity.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findById(String id);
}
