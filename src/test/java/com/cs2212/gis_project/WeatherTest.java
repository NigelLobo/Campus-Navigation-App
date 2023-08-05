/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.cs2212.gis_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Daniel Hoang
 */
public class WeatherTest {

    public WeatherTest() {
        
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("setUpClass()");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("tearDownClass()");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("setUp()");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("tearDown()");
    }


    /**
     * Test of getTodaysTemp method, of class Weather.
     */
    @Test
    public void testGetTodaysTempPass() {
        System.out.println("getTodaysTemp");
        Weather instance = new Weather();
        double result = instance.getTodaysTemp();
        boolean flag;

        if (result != Double.MAX_VALUE) {
            flag = true;
        } else {
            flag = false;
        }

        assertTrue(flag);
    }

}