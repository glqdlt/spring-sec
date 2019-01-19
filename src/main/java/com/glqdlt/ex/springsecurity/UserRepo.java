package com.glqdlt.ex.springsecurity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findById(String id);
}
