package com.cs2212.gis_project;
import java.util.ArrayList;

public class Map {
    private String mapName;
    public ArrayList<POI> listPOI = new ArrayList();

    public Map(String name) {
        this.mapName = name;
    }

    public String getName() {
        return mapName;
    }

    public void addPOI(String name,Category type, int x, int y) {
        POI newPOI = new POI(name,type,x,y, false);
        listPOI.add(newPOI);
    }

    public POI getPOI(String name) {
        for(int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if(poi.getName().equalsIgnoreCase(name)) {
                return poi;
            }
        }
        return null;
    }

    public void deletePOI(String name) {
        for(int i = 0; i < listPOI.size(); i++) {
            POI poi = (POI) listPOI.get(i);
            if(poi.getName().equalsIgnoreCase(name)) {
                listPOI.remove(i);
            }
        }
    }

    public void setListPOI(ArrayList list) {
        this.listPOI = list;
    }

    public ArrayList<POI> getPOIList() {
        return listPOI;
    }
 
}