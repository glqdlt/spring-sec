package com.glqdlt.ex.springsecurity;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * @see <a href="https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html#el-common-built-in">https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html#el-common-built-in</a>
 */
@RestController
public class ResourceRestController {

    /**
     * jsr250Enabled : RolesAllowed('...')
     * secureEnabled : Secured('...')  !! --> needs.. @EnableGlobalMethodSecurity(securedEnabled = true)
     * prePostEnabled : PreAuthorize('...') , PostAuthorize('...') !! --> needs.. @EnableGlobalMethodSecurity(prePostEnabled = true)
     * @return
     */
//    @PreAuthorize("hasRole('admin')")
//    @Secured("hasRole('admin')")
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

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String notProtectedResource(){
        return "hello";
    }

    /**
     * @see <a href="https://www.baeldung.com/spring-security-method-security">https://www.baeldung.com/spring-security-method-security</a>
     *
     *
     * consumes Content-Type 헤더를 참고함
     * produces 는 accept 헤더를 참고함
     * @return
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping(value = "/api/protected/admin/json",consumes = MediaType.APPLICATION_JSON_VALUE)
    public HashMap protectedJsonData(@RequestBody  String param) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(param, HashMap.class);
    }

}
