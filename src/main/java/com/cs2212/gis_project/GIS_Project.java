package com.cs2212.gis_project;

/**
 * This is a NetBeans starter project for CS2212.
 * 
 * It contains the pom.xml settings you need for JUint, org.json, and
 * org.json.simple. It will also work fine if you are using Java Swing.
 * 
 * If you are using JavaFX you will also have to install the JavaFX run time
 * and configure the project for JavaFX, see: https://openjfx.io/openjfx-docs/#introduction
 * 
 * GUIExample.java is a very simple GUI example using Java Swing and loading 
 * images. It has some of the very basic elements you might need for the GIS
 * GUI.
 * 
 * JSONDemo.java is a very simple example of using org.json.simple to load some
 * data unrelated to the project. It might also be helpful to show how to load
 * files from the src/resources folder.
 * 
 * src/test/java/com/cs2212/gis_project/GIS_ProjectTest.java contains a simple
 * example of a unit test for the sayHelloTo() method in this file.
 * 
 * GIS_Project.java (this file) just contains a simple hello world example and
 * some JavaDoc comments you can generate a JavaDoc for.
 * 
 * @author Daniel Servos
 * @version 1.0
 */

public class GIS_Project {
    /**
     * This field stores the name that the method sayHelloTo will say hello to.
     * 
     * @see sayHelloTo
     */
    public String name = "World";
    
    /**
     * This simple method creates a string saying hello to the person named in
     * the name field.
     * 
     * @see name
     * @return a string saying hello to name.
     */
    public String sayHelloTo() {
        return "Hello " + this.name + "!";
    }
    
    /**
     * This programs main method.
     * 
     * @param args the arguments passed to this program.
     */
    public static void main(String[] args) {
        GIS_Project gis = new GIS_Project();
        
        System.out.println(gis.sayHelloTo());
    }
}
