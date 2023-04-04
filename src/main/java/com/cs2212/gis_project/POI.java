package com.cs2212.gis_project;


public class POI {
    
    private String name;
    private Category category;
    private int[] position = new int [2];
    private boolean active = true;
    private boolean isFavourite = false;
    
    public POI(String name, Category category, int x, int y, boolean isFav) {
        this.name = name;
        this.category = category;
        this.position = new int[]{x,y};
        this.isFavourite = isFav;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        this.name = newName;
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
     
    public void setFavouriteStatus(boolean makeFavourite) {
        this.isFavourite = makeFavourite;
    }
    
    public boolean getFavouriteStatus() {
        return this.isFavourite;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (name.equals(((POI)other).getName()) &&
            category == ((POI)other).getType() &&
            position[0] == ((POI)other).position[0] &&
            position[1] == ((POI)other).position[1] &&
            isFavourite == ((POI)other).isFavourite)
            return true;
        
        return false;
    }
}
