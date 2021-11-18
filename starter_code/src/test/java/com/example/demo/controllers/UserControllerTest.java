package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository= mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository",cartRepository);
        TestUtils.injectObject(userController,"bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void testCreateUser(){
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("Hello World");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");

        ResponseEntity<User> user = userController.createUser(createUserRequest);
        assertNotNull(user);
        assertEquals(200, user.getStatusCodeValue());

        User u = user.getBody();
        assertNotNull(u);
        assertEquals("test",u.getUsername());
        assertEquals("Hello World", u.getPassword());
        when(userRepository.findByUsername(u.getUsername())).thenReturn(u);
        when(userRepository.findById(u.getId())).thenReturn(Optional.of(u));

        ResponseEntity<User> foundUser = userController.findByUserName(createUserRequest.getUsername());
        ResponseEntity<User> foundId = userController.findById(u.getId());
        assertEquals(200, foundUser.getStatusCodeValue());
        assertEquals(200, foundId.getStatusCodeValue());
        assertNotNull(foundUser);
        assertNotNull(foundId);

    }

}

