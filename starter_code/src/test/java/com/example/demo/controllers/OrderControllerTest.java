package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setup(){
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
        TestUtils.injectObject(orderController, "userRepository", userRepository);
    }

    @Test
    public void testSubmit(){
        UserOrder userOrder = new UserOrder();

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");

        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));

        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf(35));
        user.setCart(cart);

        userOrder.setId(1L);
        userOrder.setUser(user);
        userOrder.setItems(items);
        userOrder.setTotal(BigDecimal.valueOf(35));

        when(userRepository.findByUsername("test")).thenReturn(user);
        ResponseEntity<UserOrder> orderResponse = orderController.submit("test");
        assertNotNull(orderResponse);
        assertEquals(200, orderResponse.getStatusCodeValue());
        UserOrder order = orderResponse.getBody();
        assertNotNull(order);
        assertEquals(userOrder.getUser(),order.getUser());

    }

    @Test
    public void testGetOrdersForUser(){
        UserOrder userOrder = new UserOrder();

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");

        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));

        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf(35));
        user.setCart(cart);

        userOrder.setId(1L);
        userOrder.setUser(user);
        userOrder.setItems(items);
        userOrder.setTotal(BigDecimal.valueOf(35));
        List<UserOrder> orders = new ArrayList<>();
        orders.add(userOrder);

        when(userRepository.findByUsername("test")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(orders);
        ResponseEntity<List<UserOrder>> orderResponse = orderController.getOrdersForUser("test");
        assertNotNull(orderResponse);
        assertEquals(200, orderResponse.getStatusCodeValue());
        List<UserOrder> o = orderResponse.getBody();
        assertNotNull(o);
        assertEquals(1, o.size());

    }
}
