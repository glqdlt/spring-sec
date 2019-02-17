package com.glqdlt.ex.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author glqdlt
 * WithMockUser vs WithUserDetail
 * @see <a href="https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/test-method.html">https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/test-method.html</a>
 *
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


    @WithAnonymousUser
    @Test
    public void notProtectedResource_로그인을하지않은사용자() throws Exception {
        final String _stub = "hello";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_stub));
    }

    @WithAnonymousUser
    @Test
    public void protectedAdminResource_로그인을하지않은사용자() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/admin"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @WithMockUser(username = "guest")
    @Test
    public void protectedAdminResource_로그인을했지만권한이없음() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/admin"))
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "adminUser",roles = "admin")
    @Test
    public void protectedAdminResource() throws Exception {
        final String _adminStub = "admin";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_adminStub));
    }

    @WithMockUser(username = "userUser",roles = "user")
    @Test
    public void protectedUserResource() throws Exception {
        final String _userStub = "user";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_userStub));
    }

    @WithMockUser(username = "adminUser",roles = "admin")
    @Test
    public void protectedUserResource_admin_권한이접근했을때() throws Exception {
        final String _userStub = "user";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/protected/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(_userStub));
    }

    /**
     * WithUserDetails : <a href ='https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/test-method.html#test-method-withuserdetails'>@WithUserDetails</a>
     * @see <a href="https://stackoverflow.com/questions/21749781/why-i-received-an-error-403-with-mockmvc-and-junit">https://stackoverflow.com/questions/21749781/why-i-received-an-error-403-with-mockmvc-and-junit</a>
     * @throws Exception
     */
    @WithMockUser(username="jsonUser",roles = "admin")
    @Test
    public void protectedJsonData() throws Exception {
        Map<String,String> body = new HashMap<>();
        body.put("userName","jhun");
        body.put("userId","glqdlt");
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(body);
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,"/api/protected/admin/json")
                .content(content)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(content))
                .andDo(print());
    }
}
