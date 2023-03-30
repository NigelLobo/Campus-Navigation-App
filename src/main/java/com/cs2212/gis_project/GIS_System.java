/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.gis_project;

/**
 *
 * @author kashi
 */
public class GIS_System {
    
    private static final GIS_System s_Instance = new GIS_System();
    private User currentUser = null;
    
    private GIS_System() {}
    
    public static GIS_System getInstance()
    {
        return s_Instance;
    }
    
    /**
     * 
     * @param userName
     * @param password
     * @return 
     */
    public boolean login(String userName, String password)
    {
        return true; //change
    }
}
