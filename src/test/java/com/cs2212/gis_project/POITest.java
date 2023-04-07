package com.cs2212.gis_project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Charmaine
 */
public class POITest {
    
    public POITest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getName method, of class POI.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        POI instance = new POI("MC 203", Category.CLASSROOM , 0,0, false);
        String expResult = "MC 203";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class POI.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String newName = "AH 100";
        POI instance = new POI("MC 203", Category.CLASSROOM , 0,0, false);
        instance.setName(newName);
        assertTrue(instance.getName().equals("AH 100"));
    }

    /**
     * Test of getType method, of class POI.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        POI instance = new POI("MC 203",Category.CLASSROOM , 0,0, false);
        Category expResult = Category.CLASSROOM;
        Category result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class POI.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Category type = Category.ELEVATOR;
        POI instance = new POI("MC 203", Category.CLASSROOM , 0,0, false);
        instance.setType(type);
        assertTrue(instance.getType() == Category.ELEVATOR);
        
    }

    /**
     * Test of getPosition method, of class POI.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        POI instance = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        int[] expResult = {3,4} ;
        int[] result = instance.getPosition();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setPosition method, of class POI.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        int[] expResult = new int [2];
        int x = 3;
        expResult[0] = x;
        int y = 5;
        expResult[1] = y;
        POI instance = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        instance.setPosition(x, y);
        assertArrayEquals(expResult, instance.getPosition());
    }

    /**
     * Test of getActive method, of class POI.
     */
    @Test
    public void testGetActive() {
        System.out.println("getActive");
        boolean expResult = true;
        assertTrue(expResult);
    }

    /**
     * Test of setActive method, of class POI.
     */
    @Test
    public void testSetActive() {
        System.out.println("setActive");
        POI instance = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        instance.setActive(false);
        assertTrue(instance.getActive() == false);
    }
    

    /**
     * Test of setFavouriteStatus method, of class POI.
     */
    @Test
    public void testSetFavouriteStatus() {
        System.out.println("setFavouriteStatus");
        POI instance = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        instance.setFavouriteStatus(true);
        assertTrue(instance.getFavouriteStatus() == true);
    }

    /**
     * Test of getFavouriteStatus method, of class POI.
     */
    @Test
    public void testGetFavouriteStatus() {
        System.out.println("getFavouriteStatus");
        boolean expResult = false;
        assertFalse(expResult);
    }
    

    /**
     * Test of equals method, of class POI.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object other = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        POI instance = new POI("MC 203", Category.CLASSROOM , 3,4, false);
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
    }
    
}