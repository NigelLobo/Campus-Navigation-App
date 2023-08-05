package com.cs2212.gis_project;

/**
 * This class represents a POI in the application
 *
 * @author Charmaine
 */
public class POI {

    /* Name of the POI */
    private String name;
    
    /* The type of POI */
    private Category category;
    
    /* Absolute posiiton on the map */
    private int[] position = new int[2];

    /* Whether or not the current POI should be displayed */
    private boolean active = true;

    /* Whether or not the current POI is a favourite of the user */
    private boolean isFavourite = false;

    /**
     * Constructor for the POI class
     *
     * @param name the name of the POI.
     * @param category what layer type it belongs to.
     * @param x the x position of the POI on the map.
     * @param y the y position of the POI on the map.
     * @param isFav whether or not the POI is a favourite of the user
     */
    public POI(String name, Category category, int x, int y, boolean isFav) {
        this.name = name;
        this.category = category;
        this.position = new int[]{x, y};
        this.isFavourite = isFav;
    }

    /**
     * Getter method for the name.
     *
     * @return the name of the POI.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the name.
     *
     * @param newName the new name of the POI.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Getter method for the layer type.
     *
     * @return the Category of the POI.
     */
    public Category getType() {
        return category;
    }

    /**
     * Setter method for the layer type.
     *
     * @param type the new Category type of the POI.
     */
    public void setType(Category type) {
        this.category = type;
    }

    /**
     * Getter method for the position.
     *
     * @return an int array of length 2 containing the position where index 0 is
     * the xpos and index 1 is the ypos.
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Setter method for the position.
     *
     * @param x the new x position of the POI.
     * @param y the new y position of the POI.
     */
    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    /**
     * Getter method for the active status of the POI.
     *
     * @return true if the POI is active, false if not.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Setter method for the active status of the POI.
     *
     * @param isActive whether or not the POI should be active or not.
     */
    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    /**
     * Setter method for the favourite status of the POI.
     *
     * @param makeFavourite whether or not the POI is a favourite.
     */
    public void setFavouriteStatus(boolean makeFavourite) {
        this.isFavourite = makeFavourite;
    }

    /**
     * Getter method for the favourite status of the POI.
     *
     * @return true if the POI is a favourite, false if not.
     */
    public boolean getFavouriteStatus() {
        return this.isFavourite;
    }

    /**
     * Override method for determining if two POI objects are equal to each
     * other.
     *
     * @param other the other POI object to be compared.
     * @return true if the objects variables are all equal on a deep level,
     * false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (name.equals(((POI) other).getName())
                && category == ((POI) other).getType()
                && position[0] == ((POI) other).position[0]
                && position[1] == ((POI) other).position[1]
                && isFavourite == ((POI) other).isFavourite) {
            return true;
        }

        return false;
    }
}
