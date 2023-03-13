/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.gis_project;

import java.util.*;

/**
 *
 * @author nlobo9
 */
public class Application {
    
    private String activeMap;
    private HashMap<Category, Boolean> activeLayers = new HashMap<>();
    
    /**
     * Constructor for Application
     */
    public Application() {
     //initialize the activeLayers hashmap. Set all layers to be visible by default. 
        this.activeLayers.put(Category.CLASSROOM, true);
        this.activeLayers.put(Category.RESTAURANT, true);
        this.activeLayers.put(Category.LAB, true);
        this.activeLayers.put(Category.WASHROOM, true);
        this.activeLayers.put(Category.ELEVATOR, true);
    }
    
    //main method??
    
    //GUI swing should likely go here. look into how to design GUI in netbeans??
    
    
    /**
     * Start the application. 
     */
    public void start() {}
    
    /**
     * Exit the application. 
     */
    public void exit() {}
    
    /**
     * Finds the desired POI object from the current map.
     * @param search name of the POI eg. "MC 105"
     * @return reference to desired POI object
     */
    public POI findPOI(String search) {}
    
    /**
     * Changes the map that is being displayed.
     * @param name 
     */
    public void changeMap(String name) {
        //does this need to throw a custom exception?
    }
    
    /**
     * Returns the currently displayed map name
     * @return name of active map
     */
    public String getActiveMap() {
        return this.activeMap;
    }
    
    /**
     * Alters the visibility of a POI layer
     * @param type the group of layers to alter (Classroom, Lab, etc.)
     * @param active true to make visible, false to make invisible
     */
    public void toggleLayer(Category type, boolean active) {
        //update the activeLayers hashmap 
        this.activeLayers.put(type, active);
    }
}
