package com.glqdlt.ex.springsecurity;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceRestController {

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/api/protected/admin")
    public String protectedAdminResource(){
        return "admin";
    }

    @PreAuthorize("hasAnyRole('admin','user')")
    @GetMapping("/api/protected/user")
    public String protectedUserResource(){
        return "user";
    }

    @GetMapping("/")
    public String notProtectedResource(){
        return "hello";
    }

}
