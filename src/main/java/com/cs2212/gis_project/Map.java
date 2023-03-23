
package com.cs2212.gis_project;
import java.util.ArrayList;

public class Map {
    private String mapName;
    ArrayList listPOI = new ArrayList();
    
    public Map(String name) {
        this.mapName = name;
    }
    
    public String getName() {
        return mapName;
    }
    
    public void addPOI(String name,int id, Category type, int x, int y) {
        POI newPOI = new POI(name,id,type,x,y);
        listPOI.add(newPOI);
    }
    
    public POI getPOI(int id) {
        for(int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if (poi.getID() == id) {
                return poi;
            }
        }
        return null;
    }
    
    public POI getPOI(String name) {
        for(int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if(poi.getName().equals(name)) {
                return poi;
            }
        }
        return null;
    }
    
    public void deletePOI(int id) {
        for(int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if(poi.getID() == id) {
                listPOI.remove(i);
            }
        }
    }
}
