package com.covidtest.productms;

import com.covidtest.productms.model.Product;
import com.covidtest.productms.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for ProductController
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Repository that handle product database requests
     */
    @Autowired
    ProductRepository productRepository;

    /**
     * Base product url
     */
    private final String baseUrl = "/api/v1/products/";

    /**
     * Keep initial product id
     */
    private Long productId;

    /**
     * Mapper to convert object into JSON
     */
    private ObjectMapper mapper;

    /**
     * Constructor
     */
    public ProductControllerTest() {
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Initialize data before each test
     *
     * @throws Exception
     */
    @BeforeEach
    void initData() throws Exception {
        Product product = new Product();
        product.setName("PCR");
        product.setValidityDuration(30);
        product.setUseInstructions("PCR Instructions");

        product = productRepository.save(product);
        productId = product.getId();
    }

    /**
     * Clean data after each test
     */
    @AfterEach
    void eraseData() {
        productRepository.deleteAll();
    }

    /**
     * Test get all products with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetProducts_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isOk());
    }

    /**
     * Test get all products with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetProducts_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isForbidden());
    }

    /**
     * Test get one valid product with valid user role and expect Ok response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndValidUser_whenGetProduct_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + productId))
                .andExpect(status().isOk());
    }

    /**
     * Test get one invalid product with valid user role and expect Not Found response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidProductAndValidUser_whenGetProduct_thenNotFound() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + (productId + 1)))
                .andExpect(status().isNotFound());
    }

    /**
     * Test get one valid product with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndInvalidUser_whenGetProduct_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + productId))
                .andExpect(status().isForbidden());
    }

    /**
     * Test post one product with valid user role and expect OK response and id attribute
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenProductAndValidUser_whenPostProduct_thenOk() throws Exception {
        configureSecurityContext("test", "admin");

        Product product = new Product();
        product.setName("Antigenic");
        product.setValidityDuration(10);
        product.setUseInstructions("Antigenic Instructions");

        String requestBody =  mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(status().isOk());
    }

    /**
     * Test post one product with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenProductAndInvalidUser_whenPostProduct_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "delivery_man", "government");

        Product product = new Product();
        product.setName("Antigenic");
        product.setValidityDuration(10);
        product.setUseInstructions("Antigenic Instructions");

        String requestBody =  mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    /**
     * Test update one valid product with valid user role and expect OK response with new data
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndValidUser_whenPutProduct_thenOk() throws Exception {
        configureSecurityContext("test", "admin");

        Product product = new Product();
        product.setName("Antigenic");
        product.setValidityDuration(10);
        product.setUseInstructions("Antigenic Instructions");

        String requestBody =  mapper.writeValueAsString(product);

        product.setId(productId);
        String expectedResponseBody =  mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(content().json(expectedResponseBody))
                .andExpect(status().isOk());
    }

    /**
     * Test update one invalid product with valid user role and expect Not Found response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidProductAndValidUser_whenPutProduct_thenNotFound() throws Exception {
        configureSecurityContext("test", "admin");

        Product product = new Product();
        product.setName("Antigenic");
        product.setValidityDuration(10);
        product.setUseInstructions("Antigenic Instructions");

        String requestBody =  mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + (productId + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound());
    }

    /**
     * Test update one valid product with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndInvalidUser_whenPutProduct_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "delivery_man", "government");

        Product product = new Product();
        product.setName("Antigenic");
        product.setValidityDuration(10);
        product.setUseInstructions("Antigenic Instructions");

        String requestBody =  mapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    /**
     * Test delete one valid product with valid user role and expect Ok response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndValidUser_whenDeleteProduct_thenOk() throws Exception {
        configureSecurityContext("test", "admin");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(baseUrl + productId))
                .andExpect(status().isOk());
    }

    /**
     * Test delete one invalid product with valid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidProductAndValidUser_whenDeleteProduct_thenNotFound() throws Exception {
        configureSecurityContext("test", "admin");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(baseUrl + (productId + 1)))
                .andExpect(status().isNotFound());
    }

    /**
     * Test delete one valid product with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidProductAndInvalidUser_whenDeleteProduct_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "delivery_man", "government");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(baseUrl + productId))
                .andExpect(status().isForbidden());
    }

    /**
     * Mock the security context to simulate user with specific roles
     *
     * @param username Name of the mocked user
     * @param roles Roles of the mocked user
     */
    private void configureSecurityContext(String username, String... roles) {
        final var principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);

        final var account = mock(OidcKeycloakAccount.class);
        when(account.getRoles()).thenReturn(Set.of(roles));
        when(account.getPrincipal()).thenReturn(principal);

        final var authentication = mock(KeycloakAuthenticationToken.class);
        when(authentication.getAccount()).thenReturn(account);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
