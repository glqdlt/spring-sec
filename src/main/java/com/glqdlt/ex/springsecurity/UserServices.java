package com.glqdlt.ex.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    @Transactional(transactionManager = "transactionManager")
    public User findById(String id) {
        return userRepo.findById(id);
    }
}
