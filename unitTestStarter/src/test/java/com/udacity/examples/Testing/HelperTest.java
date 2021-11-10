package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {
    @Test
    public void test(){
        assertEquals(4, 4);
    }

    @Test
    public void testGetCount(){
        List<String> list = Arrays.asList("Hello","World");
        long count = Helper.getCount(list);
        assertEquals(2,count);
    }

    @Test
    public void testGetMergedList(){
        List<String> list = Arrays.asList("Hi", "","There");
        String mergedList = Helper.getMergedList(list);
        assertEquals("Hi, There",mergedList);
    }
}
