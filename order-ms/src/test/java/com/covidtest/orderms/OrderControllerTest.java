package com.covidtest.orderms;

import com.covidtest.orderms.dto.ShopOrderEntryDto;
import com.covidtest.orderms.dto.UnidentifiedShopOrderDto;
import com.covidtest.orderms.model.ShopOrder;
import com.covidtest.orderms.model.ShopOrderEntry;
import com.covidtest.orderms.repository.ShopOrderRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for OrderController
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Repository that handle order database requests
     */
    @Autowired
    ShopOrderRepository orderRepository;

    /**
     * Base order url
     */
    private final String baseUrl = "/api/v1/orders/";

    /**
     * Mapper to convert object into JSON
     */
    private ObjectMapper mapper;

    /**
     * Initial order
     */
    private ShopOrder initialOrder;

    /**
     * Constructor
     */
    public OrderControllerTest() {
        this.mapper = new ObjectMapper();
    }

    /**
     * Initialize data before each test
     *
     * @throws Exception
     */
    @BeforeEach
    void initData() throws Exception {
        ShopOrder order = new ShopOrder();
        order.setDelivered(false);
        order.setDeliveryMan(null);
        order.setOrderedAt(new Date());
        order.setUser("test");
        order.setDeliveryAddress("Test address");
        List<ShopOrderEntry> entries = new ArrayList<>();
        for (int i=1; i < 3; i++) {
            ShopOrderEntry entry = new ShopOrderEntry();
            entry.setProductId((long) i);
            entry.setQuantity(i);
            entries.add(entry);
        }
        order.setOrderEntries(entries);

        order = orderRepository.save(order);
        this.initialOrder = order;
    }

    /**
     * Clean data after each test
     */
    @AfterEach
    void eraseData() {
        orderRepository.deleteAll();
    }

    /**
     * Test get all orders with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetOrders_thenOk() throws Exception {
        configureSecurityContext("test", "government");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(initialOrder.getId()));
    }

    /**
     * Test get all orders with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetOrders_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "admin", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "all"))
                .andExpect(status().isForbidden());
    }

    /**
     * Test get all orders of the connected user with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetConnectedUserOrders_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(initialOrder.getId()));
    }

    /**
     * Test get all orders of the connected user with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetConnectedUserOrders_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl))
                .andExpect(status().isForbidden());
    }

    /**
     * Test get all orders which are not already assigned to a delivery man for the delivery
     * with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetUnassignedOrders_thenOk() throws Exception {
        configureSecurityContext("test", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "unassigned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(initialOrder.getId()));
    }

    /**
     * Test get all orders which are not already assigned to a delivery man for the delivery
     * with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetUnassignedOrders_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "admin", "government");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "unassigned"))
                .andExpect(status().isForbidden());
    }

    /**
     * Test get all orders assigned to a specific delivery man but not delivered for the moment
     * with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidUser_whenGetAssignedNotDeliveredOrders_thenOk() throws Exception {
        configureSecurityContext("test", "delivery_man");

        ShopOrder order = new ShopOrder();
        order.setDelivered(false);
        order.setDeliveryMan("test");
        order.setOrderedAt(new Date());
        order.setUser("test2");
        order.setDeliveryAddress("Test address");
        List<ShopOrderEntry> entries = new ArrayList<>();
        for (int i=1; i < 3; i++) {
            ShopOrderEntry entry = new ShopOrderEntry();
            entry.setProductId((long) i);
            entry.setQuantity(i);
            entries.add(entry);
        }
        order.setOrderEntries(entries);

        order = orderRepository.save(order);

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "assigned"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    /**
     * Test get all orders assigned to a specific delivery man but not delivered for the moment
     * with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidUser_whenGetAssignedNotDeliveredOrders_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "admin", "government");

        mockMvc.perform(MockMvcRequestBuilders
                .get(baseUrl + "assigned"))
                .andExpect(status().isForbidden());
    }

    /**
     * Test post one order with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenOrderAndValidUser_whenPostOrder_thenOk() throws Exception {
        configureSecurityContext("test", "customer");

        UnidentifiedShopOrderDto order = new UnidentifiedShopOrderDto();
        order.setDeliveryAddress("Test address");
        List<ShopOrderEntryDto> entries = new ArrayList<>();
        for (int i=1; i < 3; i++) {
            ShopOrderEntryDto entry = new ShopOrderEntryDto();
            entry.setProductId((long) i);
            entry.setQuantity(i);
            entries.add(entry);
        }
        order.setOrderEntries(entries);

        String requestBody =  mapper.writeValueAsString(order);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.user").value("test"))
                .andExpect(status().isOk());
    }

    /**
     * Test post one order with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenOrderAndInvalidUser_whenPostOrder_thenForbidden() throws Exception {
        configureSecurityContext("test", "");

        UnidentifiedShopOrderDto order = new UnidentifiedShopOrderDto();
        order.setDeliveryAddress("Test address");
        List<ShopOrderEntryDto> entries = new ArrayList<>();
        for (int i=1; i < 3; i++) {
            ShopOrderEntryDto entry = new ShopOrderEntryDto();
            entry.setProductId((long) i);
            entry.setQuantity(i);
            entries.add(entry);
        }
        order.setOrderEntries(entries);

        String requestBody =  mapper.writeValueAsString(order);

        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isForbidden());
    }

    /**
     * Test assign the connected user to an order with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidOrderAndValidUser_whenPutAssignOrder_thenOk() throws Exception {
        configureSecurityContext("test", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + initialOrder.getId() + "/assign"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").value("test"));
    }

    /**
     * Test assign the connected user to an invalid order with valid user role and expect NotFound response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidOrderAndValidUser_whenPutAssignOrder_thenNotFound() throws Exception {
        configureSecurityContext("test", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + (initialOrder.getId() + 1) + "/assign"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test assign the connected user to an order with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidOrderAndInvalidUser_whenPutAssignOrder_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "government", "admin");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + initialOrder.getId() + "/assign"))
                .andExpect(status().isForbidden());
    }

    /**
     * Test set an order as delivered with valid user role and expect OK response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidOrderAndValidUser_whenPutDeliveredOrder_thenOk() throws Exception {
        configureSecurityContext("test", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + initialOrder.getId() + "/delivered"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delivered").value(true));
    }

    /**
     * Test set an invalid order as delivered with valid user role and expect NotFound response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenInvalidOrderAndValidUser_whenPutDeliveredOrder_thenNotFound() throws Exception {
        configureSecurityContext("test", "delivery_man");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + (initialOrder.getId() + 1) + "/delivered"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test set an order as delivered with invalid user role and expect Forbidden response
     *
     * @throws Exception Throws Exception if response not expected
     */
    @Test
    void givenValidOrderAndInvalidUser_whenPutDeliveredOrder_thenForbidden() throws Exception {
        configureSecurityContext("test", "customer", "government", "admin");

        mockMvc.perform(MockMvcRequestBuilders
                .put(baseUrl + initialOrder.getId() + "/delivered"))
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
