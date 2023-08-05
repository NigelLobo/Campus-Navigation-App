package com.cs2212.gis_project;

import java.util.ArrayList;

/**
 * This class represents a Map in the application. A map is characterized by its
 * name, and a collection of POI's.
 *
 * @author Charmaine
 */
public class Map {

    /* Name of the map */
    private String mapName;
    
    /* ArrayList of POI objects */
    public ArrayList<POI> listPOI = new ArrayList();

    /**
     * Constructor of the map object.
     *
     * @param name the name of the map.
     */
    public Map(String name) {
        this.mapName = name;
    }

    /**
     * Getter method for the name.
     *
     * @return the name of the map.
     */
    public String getName() {
        return mapName;
    }

    /**
     * Method for adding new POI's to the ArrayList.
     *
     * @param name the name of the POI.
     * @param type the layer type of the POI.
     * @param x the x position of the POI.
     * @param y the y position of the POI.
     */
    public void addPOI(String name, Category type, int x, int y) {
        POI newPOI = new POI(name, type, x, y, false);
        listPOI.add(newPOI);
    }

    /**
     * Method for finding and retrieving a POI from the list.
     *
     * @param name the name of the desired POI.
     * @return the POI object with the matching name, or null if it could not be
     * found.
     */
    public POI getPOI(String name) {
        for (int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if (poi.getName().equalsIgnoreCase(name)) {
                return poi;
            }
        }
        return null;
    }

    /**
     * Method for removing a POI from the list.
     *
     * @param name the name of the desired POI to be removed.
     */
    public void deletePOI(String name) {
        for (int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if (poi.getName().equalsIgnoreCase(name)) {
                listPOI.remove(i);
            }
        }
    }

    /**
     * Given an ArrayList, this method will set our list to point to the one
     * given.
     *
     * @param list the new ArrayList of POI's.
     */
    public void setListPOI(ArrayList list) {
        this.listPOI = list;
    }

    /**
     * Getter method for the POI list.
     *
     * @return a reference to the ArrayList containing the POI's.
     */
    public ArrayList<POI> getPOIList() {
        return listPOI;
    }

    /**
     * Override method for determining if two map objects are the same. The
     * method will do a deep comparison of the POI objects inside each Map
     * objects array list, and will also compare their names.
     *
     * @param other the other Map object to be compared.
     * @return true if their contents are both the same, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!this.getName().equals(((Map) other).getName())) {
            return false;
        }

        if (this.listPOI.size() != ((Map) other).listPOI.size()) {
            return false;
        }

        for (int i = 0; i < this.listPOI.size(); i++) {
            if (!this.listPOI.get(i).equals(((Map) other).listPOI.get(i))) {
                return false;
            }
        }

        return true;
    }

}
