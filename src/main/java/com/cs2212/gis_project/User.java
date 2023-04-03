/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.gis_project;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a user.
 * A user is represented by a username, and contains a list of favourite POI's.
 * A user also may or may not be an admin.
 * @author Rafay Kashif
 */
public class User {
    
    /* IsAdmin and Username should not be modified after they are set. A user should not be able to change their username or their admin status */
    private final boolean m_IsAdmin;
    private final String m_Username;
    private ArrayList<Integer> m_Favourites;

    /**
     * Constructor for User. By default, the user is not an admin and has zero favourites.
     * @param username the username of the user
     */
    public User(String username)
    {
        m_Username = username;
        m_IsAdmin = false;
        m_Favourites = new ArrayList<Integer>();
    }
    
    /**
     * Constructor for the user. By default the user has zero favourites.
     * @param username the username of the user
     * @param isAdmin whether or not the user should be considered an admin
     */
    public User(String username, boolean isAdmin)
    {
        m_Username = username;
        m_IsAdmin = isAdmin;
        m_Favourites = new ArrayList<Integer>();
    }
    
    /**
     * Constructor for the user.
     * @param username the username of the user
     * @param isAdmin whether or not the user should be considered an admin
     * @param favourites a list of POI id's that represent the favourites of the user
     */
    public User(String username, boolean isAdmin, ArrayList<Integer> favourites)
    {
        m_Username = username;
        m_IsAdmin = isAdmin;
        m_Favourites = favourites;
    }
    
    /**
     * Adds a POI to the users favourite list
     * @param id the ID of the POI to be added
     */
    public void addFavourite(Integer id)
    {
        m_Favourites.add(id);
    }

    /**
     * Removes a POI from the users favourite list
     * @param id the ID of the POI to be removed
     */
    public void removeFavourite(Integer id)
    {
        m_Favourites.remove(id);
    }

    /**
     * Gets the amount of favourites the user has.
     * @return the size of the list of favourites
     */
    public int getFavouriteLength()
    {
        return m_Favourites.size();
    }

    /**
     * Checks whether or not the user is an admin.
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin()
    {
        return m_IsAdmin;
    }
    
}
