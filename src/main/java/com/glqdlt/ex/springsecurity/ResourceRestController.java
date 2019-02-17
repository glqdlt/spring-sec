package com.glqdlt.ex.springsecurity;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceRestController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/protected/area")
    public String protectedResource(){
        return "good";
    }

    @GetMapping("/")
    public String notProtectedResource(){
        return "hello";
    }

}
