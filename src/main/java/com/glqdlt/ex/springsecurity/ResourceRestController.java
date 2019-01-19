package com.glqdlt.ex.springsecurity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceRestController {

    @GetMapping("/api/protected/area")
    public String protectedResource(){
        return "good";
    }

    @GetMapping("/")
    public String notProtectedResource(){
        return "hello";
    }

}
