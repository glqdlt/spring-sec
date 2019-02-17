package com.glqdlt.ex.springsecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author glqdlt
 * @see <a href = "https://www.baeldung.com/spring-security-integration-tests">https://www.baeldung.com/spring-security-integration-tests</a>
 * 2019-02-17
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ResourceRestController.class})
public class WebSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "testUser")
    @Test
    public void rootExpectedHelloString() throws Exception {
        final String _stub = "hello";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_stub));
    }

    @WithMockUser(username = "testUser")
    @Test
    public void rootExpectedHelloString_권한없어서접근이거부() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/admin"))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }
    @WithMockUser(username = "adminUser",roles = "admin")
    @Test
    public void protectedAdminRole() throws Exception {
        final String _adminStub = "admin";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_adminStub));
    }

    @WithMockUser(username = "userUser",roles = "user")
    @Test
    public void protectedUserRole() throws Exception {
        final String _userStub = "user";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_userStub));
    }

    @WithMockUser(username = "adminUser",roles = "admin")
    @Test
    public void protectedUserRole_AdminUser() throws Exception {
        final String _userStub = "user";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_userStub));
    }

}
