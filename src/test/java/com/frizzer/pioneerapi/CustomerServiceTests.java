package com.frizzer.pioneerapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frizzer.pioneerapi.domain.dto.CustomerLoginDto;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CustomerServiceTests {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres").withDatabaseName("test")
                                                                                                .withUsername("test").withPassword("test")
                                                                                                .withInitScript(
                                                                                                        "init.sql");
    @Container
    public static RedisContainer redisContainer = new RedisContainer("redis");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379).toString());
    }

    @Test
    void testFilterOk() throws Exception {
        CustomerLoginDto loginDto = new CustomerLoginDto();
        loginDto.setEmail("frizzer@gmail.com");
        loginDto.setPassword("12345678");

        String token = performLogin(loginDto);

        String goodEmail = "frizzer@gmail.com";

        MvcResult filterResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer/filter")
                                                                       .header("Authorization", "Bearer " + token)
                                                                       .param("email", goodEmail)
                                                                       .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isOk())
                                        .andReturn();

        MockHttpServletResponse response = filterResult.getResponse();
        assertTrue(response.getContentAsString().contains(goodEmail));
    }

    @Test
    void testFilterEmpty() throws Exception {
        CustomerLoginDto loginDto = new CustomerLoginDto();
        loginDto.setEmail("frizzer@gmail.com");
        loginDto.setPassword("12345678");

        String token = performLogin(loginDto);

        String fakeEmail = "false@gmail.com";

        MvcResult filterResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer/filter")
                                                                       .header("Authorization", "Bearer " + token)
                                                                       .param("email", fakeEmail)
                                                                       .contentType(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isOk())
                                        .andReturn();

        MockHttpServletResponse response = filterResult.getResponse();
        assertEquals("[]", response.getContentAsString());
    }

    private String performLogin(CustomerLoginDto loginDto) throws Exception {
        MvcResult result = mockMvc.perform(post("/customer/login").contentType(MediaType.APPLICATION_JSON)
                                                                  .content(objectMapper.writeValueAsString(loginDto)))
                                  .andExpect(status().isOk())
                                  .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();
        assertNotNull(token);
        return token;
    }

}
