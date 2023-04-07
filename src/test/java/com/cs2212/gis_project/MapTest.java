package com.cs2212.gis_project;

import java.util.ArrayList;
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
public class MapTest {
    
    public MapTest() {
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
     * Test of getName method, of class Map.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Map instance = new Map("AH");
        String expResult = "AH";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addPOI method, of class Map.
     */
    @Test
    public void testAddPOI() {
        System.out.println("addPOI");
        String name = "AH 201";
        Category type = Category.CLASSROOM;
        int x = 30;
        int y = 20;
        Map instance = new Map ("AH");
        int prevSize = instance.listPOI.size();
        instance.addPOI(name, type, x, y);
        int afterSize = instance.listPOI.size();
        assertEquals(prevSize + 1, afterSize);
       
        
    }

     /**
     * Test of getPOI method, of class Map.
     */
    @Test
    public void testGetPOI() {
        System.out.println("getPOI");
        String name = "AH 201";
        Category type = Category.CLASSROOM;
        int x = 30;
        int y = 20;
        Map instance = new Map ("AH");
        instance.addPOI(name, type, x, y);
        POI result = instance.getPOI(name);
        assertNotNull(result);

    }

    /**
     * Test of deletePOI method, of class Map.
     */
    @Test
    public void testDeletePOI() {
        System.out.println("deletePOI");
        String name = "AH 201";
        Category type = Category.CLASSROOM;
        int x = 30;
        int y = 20;
        Map instance = new Map ("AH");
        instance.addPOI(name, type, x, y);
        int prevSize = instance.listPOI.size();
        instance.deletePOI(name);
        int afterSize = instance.listPOI.size();
        assertEquals(prevSize - 1, afterSize);
       
    }

    /**
     * Test of setListPOI method, of class Map.
     */
    @Test
    public void testSetListPOI() {
        System.out.println("setListPOI");
        Map instance = new Map("AH");
        ArrayList list = new ArrayList<>();
        POI poi1 = new POI ("NCB 200", Category.ELEVATOR, 20, 30, false);
        POI poi2 = new POI ("AH 150", Category.CLASSROOM, 25, 33, false);
        list.add(poi1);
        list.add(poi2);
        instance.setListPOI(list);
        assertEquals(list, instance.listPOI);
    }

    /**
     * Test of getPOIList method, of class Map.
     */
    @Test
    public void testGetPOIList() {
        System.out.println("getPOIList");
        Map instance = new Map ("AH");
        POI poi1 = new POI ("NCB 200", Category.ELEVATOR, 20, 30, false);
        POI poi2 = new POI ("AH 150", Category.CLASSROOM, 25, 33, false);
        instance.listPOI.add(poi1);
        instance.listPOI.add(poi2);
        ArrayList<POI> expResult = instance.listPOI;
        ArrayList<POI> result = instance.getPOIList();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Map.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object other = new Map ("AH");
        Map instance = new Map("AH");
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
    }
    
}
