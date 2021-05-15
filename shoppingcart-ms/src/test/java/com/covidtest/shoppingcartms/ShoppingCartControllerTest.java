package com.covidtest.shoppingcartms;

import com.covidtest.shoppingcartms.dto.UnidentifiedShoppingCartEntryDto;
import com.covidtest.shoppingcartms.model.ShoppingCart;
import com.covidtest.shoppingcartms.model.ShoppingCartEntry;
import com.covidtest.shoppingcartms.repository.ShoppingCartRepository;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for ShoppingCartController
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Repository that handle shopping cart database requests
     */
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    /**
     * Base shopping cart url
     */
    private final String baseUrl = "/api/v1/cart/";

    /**
     * Keep initial shopping cart size
     */
    private int shoppingCartSize;

    /**
     * Mapper to convert object into JSON
     */
    private ObjectMapper mapper;

    /**
     * Constructor
     */
    public ShoppingCartControllerTest() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Initialize data before each test
     *
     * @throws Exception
     */
    @BeforeEach
    void initData() throws Exception {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser("test");
        List<ShoppingCartEntry> entries = new ArrayList<>();
        for (int i=1; i < 3; i++) {
            ShoppingCartEntry entry = new ShoppingCartEntry();
            entry.setProductId((long) i);
            entry.setQuantity(i);
            entries.add(entry);
        }
        shoppingCart.setEntries(entries);

        shoppingCart = shoppingCartRepository.save(shoppingCart);
        shoppingCartSize = shoppingCart.getEntries().size();
    }

    /**
     * Clean data after each test
     */
    @AfterEach
    void eraseData() {
        shoppingCartRepository.deleteAll();
    }

    /**
     * Test get the shopping cart of the connected user with valid user role
     * and expect OK response with non-null body
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetShoppingCart_thenOkAndNotNull() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    /**
     * Test get the shopping cart of the connected user (with no cart) with valid user role
     * and expect OK response and null body
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetShoppingCart_thenOkAndNull() throws Exception {
        configureSecurityContext("test2", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    /**
     * Test get the shopping cart of the connected user with invalid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetShoppingCart_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isForbidden());
    }

    /**
     * Test add line to the shopping cart of the connected user with valid user role
     * and expect OK response and line added
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenAddShoppingCartEntry_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        UnidentifiedShoppingCartEntryDto entry = new UnidentifiedShoppingCartEntryDto();
        entry.setProductId(10L);
        entry.setQuantity(3);

        String requestBody = mapper.writeValueAsString(entry);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$.entries[%d]", shoppingCartSize)).exists());
    }

    /**
     * Test add line to the shopping cart of the connected user (with no cart) with valid user role
     * and expect OK response and shopping cart created with the new line
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenAddShoppingCartEntry_thenOkAndCreate() throws Exception {
        configureSecurityContext("test2", "customer");

        UnidentifiedShoppingCartEntryDto entry = new UnidentifiedShoppingCartEntryDto();
        entry.setProductId(10L);
        entry.setQuantity(3);

        String requestBody = mapper.writeValueAsString(entry);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.entries[0]").exists());
    }

    /**
     * Test add line to the shopping cart of the connected user with invalid user role
     * and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenAddShoppingCartEntry_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        UnidentifiedShoppingCartEntryDto entry = new UnidentifiedShoppingCartEntryDto();
        entry.setProductId(10L);
        entry.setQuantity(3);

        String requestBody = mapper.writeValueAsString(entry);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    /**
     * Test delete the shopping cart of the connected user with valid user role and expect Ok response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenDeleteShoppingCart_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(baseUrl))
                .andExpect(status().isOk());
    }

    /**
     * Test delete the shopping cart of the connected user with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenDeleteShoppingCart_thenOk() throws Exception {
        configureSecurityContext("test", "");

        mockMvc.perform(MockMvcRequestBuilders
                .delete(baseUrl))
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
