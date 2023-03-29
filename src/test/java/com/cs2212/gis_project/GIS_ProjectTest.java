package com.cs2212.gis_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * My unit tests for the GIS_Project class.
 * 
 * @author Joe Bloggs
 * @version 1.0
 */
public class GIS_ProjectTest {
    
    /**
     * This method is called before we start testing this class.
     */
    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }
    
    /**
     * This method is called after we start testing this class.
     */
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    /**
     * This method is called before each unit test.
     */
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
    }
    
    /**
     * This method is called after each unit test.
     */
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }

    /**
     * Test of sayHelloTo method, of class GIS_Project.
     */
    @Test
    public void testSayHelloTo() {
        System.out.println("sayHelloTo");
        GIS_Project instance = new GIS_Project();
        instance.name = "Joe Bloggs";
        String expResult = "Hello Joe Bloggs!";
        String result = instance.sayHelloTo();
        assertEquals(expResult, result);
    }    
    
    @Test
    public void testWeather(){
        Weather lmao = new Weather();
        System.out.println(lmao.getInstance());
        
        System.out.println(lmao.getWeather());
    }
}
