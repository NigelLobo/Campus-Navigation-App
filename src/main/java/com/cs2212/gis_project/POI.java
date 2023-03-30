package com.cs2212.gis_project;


public class POI {
    
    private String name;
    private int id;
    private Category category;
    private int[] position = new int [2];
    private boolean active = true;
    
    public POI(String name, int id, Category category, int x, int y) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.position = new int[]{x,y};
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
    
    public int getID() {
        return id;
    }
    
    public Category getType() {
        return category;
    }
    
    public void setType(Category type) {
        this.category = type;
    }
    
    public int[] getPosition() {
        return position;
    }
    
    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }
    
    public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean isActive) {
        this.active = isActive;
    }
     
}
