package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddAndRemoveCart(){
        Cart cart = new Cart();

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");
        user.setCart(cart);

        cart.setId(1L);
        cart.setUser(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(5);

        when(userRepository.findByUsername(modifyCartRequest.getUsername())).thenReturn(user);
        when(itemRepository.findById(modifyCartRequest.getItemId())).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> cartAddResponse =  cartController.addTocart(modifyCartRequest);
        assertNotNull(cartAddResponse);
        assertEquals(200, cartAddResponse.getStatusCodeValue());
        Cart cAdd = cartAddResponse.getBody();
        assertNotNull(cAdd);
        assertEquals(modifyCartRequest.getUsername(), cAdd.getUser().getUsername());
        assertEquals(5, cAdd.getItems().size());

        ResponseEntity<Cart> cartRemoveResponse =  cartController.removeFromcart(modifyCartRequest);
        assertNotNull(cartRemoveResponse);
        assertEquals(200, cartRemoveResponse.getStatusCodeValue());
        Cart cRemove = cartAddResponse.getBody();
        assertNotNull(cRemove);
        assertEquals(0, cRemove.getItems().size());
    }
}