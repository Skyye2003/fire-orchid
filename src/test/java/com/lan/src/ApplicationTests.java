package com.lan.src;

import com.lan.src.dao.DiskContentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ApplicationTests {


    @Test
    void contextLoads() {
    }

    @Test
    void testDao(){
        System.out.println("Test");
    }

    @Test
    void testArrays(){
        int[] ints = Arrays.copyOfRange(new int[]{1, 2, 3}, 0, 3);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

}
