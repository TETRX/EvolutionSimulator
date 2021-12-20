package agh.project.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpperBoundTest {
    @Test
    public void testSimple(){
        List<Integer> list = Arrays.asList(1,2,3,4);
        int expected = 2, actual = UpperBound.binarySearch(list,2);
        assertEquals(expected,actual);
    }

    @Test
    public void testOnlyOne(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        int expected = 1, actual = UpperBound.binarySearch(list,1);
        assertEquals(expected,actual);
    }

    @Test
    public void testDraw(){
        List<Integer> list = Arrays.asList(1,2,2,2,2,3,4);
        int expected = 5, actual = UpperBound.binarySearch(list,2);
        assertEquals(expected,actual);
    }

    @Test
    public void testOnlyDraw(){
        List<Integer> list = Arrays.asList(2,2);
        int expected = 2, actual = UpperBound.binarySearch(list,2);
        assertEquals(expected,actual);

    }
}
