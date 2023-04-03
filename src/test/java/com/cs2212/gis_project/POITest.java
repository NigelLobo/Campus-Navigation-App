
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
        POI instance = new POI("MC 203", 0, Category.CLASSROOM , 0,0);
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
        String newName = "";
        POI instance = null;
        instance.setName(newName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class POI.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        POI instance = new POI("MC 203", 2, Category.CLASSROOM , 0,0);
        int expResult = 2;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getType method, of class POI.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        POI instance = new POI("MC 203", 2, Category.CLASSROOM , 0,0);
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
        Category type = null;
        POI instance = null;
        instance.setType(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class POI.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        POI instance = new POI("MC 203", 2, Category.CLASSROOM , 3,4);
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
        int x = 0;
        int y = 0;
        POI instance = null;
        instance.setPosition(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActive method, of class POI.
     */
    @Test
    public void testGetActive() {
        System.out.println("getActive");
        POI instance = null;
        boolean expResult = false;
        boolean result = instance.getActive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActive method, of class POI.
     */
    @Test
    public void testSetActive() {
        System.out.println("setActive");
        boolean isActive = false;
        POI instance = null;
        instance.setActive(isActive);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
