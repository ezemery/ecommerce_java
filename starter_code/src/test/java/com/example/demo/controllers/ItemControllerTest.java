package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void testGetItemsByName(){
        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));

        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findByName("Soap")).thenReturn(items);
        ResponseEntity<List<Item>> responseItems = itemController.getItemsByName("Soap");
        assertNotNull(responseItems);
        assertEquals(200, responseItems.getStatusCodeValue());
        List<Item> i = responseItems.getBody();
        assertNotNull(i);
        assertEquals(1,i.size());
    }

    @Test
    public void testGetItemById(){
        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> foundItem = itemController.getItemById(1L);
        assertNotNull(foundItem);
        assertEquals(200, foundItem.getStatusCodeValue());
        Item i = foundItem.getBody();
        assertNotNull(i);
        assertEquals("Soap", item.getName());
    }

    @Test
    public void testGetItems(){
        Item item = new Item();
        item.setId(1L);
        item.setName("Soap");
        item.setDescription("Washing soap");
        item.setPrice(BigDecimal.valueOf(19.5));

        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findAll()).thenReturn(items);
        ResponseEntity<List<Item>> responseItems = itemController.getItems();
        assertNotNull(responseItems);
        assertEquals(200, responseItems.getStatusCodeValue());
        List<Item> i = responseItems.getBody();
        assertNotNull(i);
        assertEquals(1,i.size());
    }
}
