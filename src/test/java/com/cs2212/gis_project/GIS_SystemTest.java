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
public class GIS_SystemTest {
    
    public GIS_SystemTest() {
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
     * Test of login method, of class GIS_System.
     */
    @Test
    public void testLoginPass() {
        System.out.println("login");
        String username = "Admin";
        String password = "123456";
        GIS_System instance = GIS_System.getInstance();
        boolean expResult = true;
        boolean result = instance.login("src/resources/data/temp.json", username, password);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of login method, of class GIS_System.
     */
    @Test
    public void testLoginException() {
        System.out.println("login");
        String username = "Admin";
        String password = "123456";
        GIS_System instance = GIS_System.getInstance();
        boolean expResult = false;
        boolean result = instance.login("src/resources/data/temp2.json", username, password);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of login method, of class GIS_System.
     */
    @Test
    public void testLoginFail() {
        System.out.println("login");
        String username = "Bobby";
        String password = "123456";
        GIS_System instance = GIS_System.getInstance();
        boolean expResult = false;
        boolean result = instance.login("src/resources/data/temp1.json", username, password);
        assertEquals(expResult, result);
    }


    /**
     * Test of Load method, of class GIS_System.
     */
    @Test
    public void testLoad() {
        System.out.println("Load");
        GIS_System instance = GIS_System.getInstance();
        Map[] expResult = new Map[13];
        
        expResult[0] = new Map("ALUMNI_2"); 
        expResult[1] = new Map("ALUMNI_3");
        expResult[2] = new Map("ALUMNI_4");
        expResult[3] = new Map("MIDDLESEX_2");
        expResult[4] = new Map("MIDDLESEX_3");
        expResult[5] = new Map("MIDDLESEX_4");
        expResult[6] = new Map("MIDDLESEX_5");
        expResult[7] = new Map("MIDDLESEX_6");
        expResult[8] = new Map("NORTH_CAMPUS_1");
        expResult[9] = new Map("NORTH_CAMPUS_2");
        expResult[10] = new Map("NORTH_CAMPUS_3");
        expResult[11] = new Map("NORTH_CAMPUS_4");
        expResult[12] = new Map("NORTH_CAMPUS_5");
        
        for(int i =0; i<13; i++){
            expResult[i].addPOI("Bobby", Category.CLASSROOM, 200    , 100);
        }
        
        
        
        
        Map[] result = instance.Load("src/resources/data/temp.json");
        assertArrayEquals(expResult, result);
    }
    
    
     /**
     * Test of Load method, of class GIS_System.
     */
    @Test
    public void testLoadFail() {
        System.out.println("Load");
        GIS_System instance = GIS_System.getInstance();
        Map[] expResult = new Map[13];
        
        expResult[0] = new Map("ALUMNI_2"); 
        expResult[1] = new Map("ALUMNI_3");
        expResult[2] = new Map("ALUMNI_4");
        expResult[3] = new Map("MIDDLESEX_2");
        expResult[4] = new Map("MIDDLESEX_3");
        expResult[5] = new Map("MIDDLESEX_4");
        expResult[6] = new Map("MIDDLESEX_5");
        expResult[7] = new Map("MIDDLESEX_6");
        expResult[8] = new Map("NORTH_CAMPUS_1");
        expResult[9] = new Map("NORTH_CAMPUS_2");
        expResult[10] = new Map("NORTH_CAMPUS_3");
        expResult[11] = new Map("NORTH_CAMPUS_4");
        expResult[12] = new Map("NORTH_CAMPUS_5");
        
        for(int i =0; i<13; i++){
            expResult[i].addPOI("Jimmy", Category.CLASSROOM, 200    , 100);
        }
        
        Map[] result = instance.Load("src/resources/data/");
        
        assertNull(result);
    }

    /**
     * Test of Save method, of class GIS_System.
     */
    @Test
    public void testSavePass() {
        System.out.println("Save");
        Map[] expResult = new Map[13];
        expResult[0] = new Map("ALUMNI_2"); 
        expResult[1] = new Map("ALUMNI_3");
        expResult[2] = new Map("ALUMNI_4");
        expResult[3] = new Map("MIDDLESEX_2");
        expResult[4] = new Map("MIDDLESEX_3");
        expResult[5] = new Map("MIDDLESEX_4");
        expResult[6] = new Map("MIDDLESEX_5");
        expResult[7] = new Map("MIDDLESEX_6");
        expResult[8] = new Map("NORTH_CAMPUS_1");
        expResult[9] = new Map("NORTH_CAMPUS_2");
        expResult[10] = new Map("NORTH_CAMPUS_3");
        expResult[11] = new Map("NORTH_CAMPUS_4");
        expResult[12] = new Map("NORTH_CAMPUS_5");
        for(int i =0; i<13; i++){
            expResult[i].addPOI("Bobby", Category.CLASSROOM, 200    , 100);
        }
        GIS_System instance = GIS_System.getInstance();
        instance.Save(expResult, "src/resources/data/temp.json");
        
        Map[] results = instance.Load("src/resources/data/temp.json");
        
        assertArrayEquals(expResult, results);
    }
    
    /**
     * Test of Save method, of class GIS_System.
     */
    @Test
    public void testSaveException() {
        System.out.println("Save");
        Map[] map = new Map[13];
        map[0] = new Map("ALUMNI_2"); 
        map[1] = new Map("ALUMNI_3");
        map[2] = new Map("ALUMNI_4");
        map[3] = new Map("MIDDLESEX_2");
        map[4] = new Map("MIDDLESEX_3");
        map[5] = new Map("MIDDLESEX_4");
        map[6] = new Map("MIDDLESEX_5");
        map[7] = new Map("MIDDLESEX_6");
        map[8] = new Map("NORTH_CAMPUS_1");
        map[9] = new Map("NORTH_CAMPUS_2");
        map[10] = new Map("NORTH_CAMPUS_3");
        map[11] = new Map("NORTH_CAMPUS_4");
        map[12] = new Map("NORTH_CAMPUS_5");
        for(int i =0; i<13; i++){
            map[i].addPOI("Bobby", Category.CLASSROOM, 200    , 100);
        }
        GIS_System instance = GIS_System.getInstance();
        boolean expectedResult = instance.Save(map, "src/resources/data/");
        
        assertFalse(expectedResult);
    }
    
}