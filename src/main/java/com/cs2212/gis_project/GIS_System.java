/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.gis_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Rafay Kashif
 */
public class GIS_System {
    
    private static final GIS_System s_Instance = new GIS_System();
    
    private HashMap<String, Category> stringToCategory;
    private HashMap<Category, String> categoryToString;
    
    private GIS_System() {
        stringToCategory = new HashMap<String, Category>();
        stringToCategory.put("C", Category.CLASSROOM);
        stringToCategory.put("R", Category.RESTAURANT);
        stringToCategory.put("L", Category.LAB);
        stringToCategory.put("W", Category.WASHROOM);
        stringToCategory.put("E", Category.ELEVATOR);
        stringToCategory.put("T", Category.CUSTOM);
        
        categoryToString = new HashMap<Category, String>();
        categoryToString.put(Category.CLASSROOM, "C");
        categoryToString.put(Category.RESTAURANT, "R");
        categoryToString.put(Category.LAB, "L");
        categoryToString.put(Category.WASHROOM, "W");
        categoryToString.put(Category.ELEVATOR, "E");
        categoryToString.put(Category.CUSTOM, "T");
    }
    
    public static GIS_System getInstance()
    {
        return s_Instance;
    }
    
    public boolean login(String username, String password)
    {
        try {
            //URL obj = this.getClass().getClassLoader().getResource("data/app.json"); 
            String path = "src/resources/data/app.json";
            String contents = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject buildings = new JSONObject(contents);
            JSONObject user = buildings.getJSONObject("user");
            if (username.equals(user.get("username")) && password.equals(user.get("password")))
                    return true;
            else
                return false;
        }
        catch(IOException e)
        {
           return false;
        }
    }
    
    public Map[] Load(String path)
    {
        try {
            //JSONParser parser = new JSONParser();
            //URL obj = this.getClass().getClassLoader().getResource("data/app.json");
            //String path = "src/resources/data/app.json";
            String contents = new String(Files.readAllBytes(Paths.get(path)));
            //Reader reader = new InputStreamReader(obj.openStream());
            JSONObject buildings = new JSONObject(contents);
            JSONObject alumni = buildings.getJSONObject("ALUMNI");
            JSONObject middlesex = buildings.getJSONObject("MIDDLESEX");
            JSONObject ncb = buildings.getJSONObject("NORTH_CAMPUS");
            
            Map[] maps = new Map[13];
            
            int floorLevel = 2;
            for (int i = 0; i < 3; i++)
            {
                JSONArray poiList = alumni.getJSONArray("LEVEL_" + floorLevel);
                ArrayList<POI> pois = new ArrayList<POI>();
                for (int j = 0; j < poiList.length(); j++)
                {
                    JSONObject currentPOI = poiList.getJSONObject(j);
                    String category = (String)currentPOI.get("type");
                         
                    POI poi = new POI((String)currentPOI.get("name"), stringToCategory.get(category), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"), (Boolean)currentPOI.get("isFavourite"));
                    pois.add(poi);
                }
                maps[i] = new Map("ALUMNI_" + floorLevel);
                maps[i].setListPOI(pois);
                floorLevel++;
            }
            
            floorLevel = 2;
            for (int i = 3; i < 8; i++)
            {
                JSONArray poiList = middlesex.getJSONArray("LEVEL_" + floorLevel);
                ArrayList<POI> pois = new ArrayList<POI>();
                for (int j = 0; j < poiList.length(); j++)
                {
                    JSONObject currentPOI = poiList.getJSONObject(j);
                    String category = (String)currentPOI.get("type");
                         
                    POI poi = new POI((String)currentPOI.get("name"), stringToCategory.get(category), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"), (Boolean)currentPOI.get("isFavourite"));
                    pois.add(poi);
                }
                maps[i] = new Map("MIDDLESEX_" + floorLevel);
                maps[i].setListPOI(pois);
                floorLevel++;
            }
            
            floorLevel = 1;
            for (int i = 8; i < 13; i++)
            {
                JSONArray poiList = ncb.getJSONArray("LEVEL_" + floorLevel);
                ArrayList<POI> pois = new ArrayList<POI>();
                for (int j = 0; j < poiList.length(); j++)
                {
                    JSONObject currentPOI = poiList.getJSONObject(j);
                    String category = (String)currentPOI.get("type");
                         
                    POI poi = new POI((String)currentPOI.get("name"), stringToCategory.get(category), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"), (Boolean)currentPOI.get("isFavourite"));
                    pois.add(poi);
                }
                maps[i] = new Map("NORTH_CAMPUS_" + floorLevel);
                maps[i].setListPOI(pois);
                floorLevel++;
            }
            
            return maps;
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public boolean Save(Map[] maps, String path)
    {
        JSONObject buildings = new JSONObject();
        JSONObject alumni = new JSONObject();
        JSONObject middlesex = new JSONObject();
        JSONObject ncb = new JSONObject();
        
        int floorLevel = 2;
        for (int i = 0; i < 3; i++)
        {
            JSONArray poiList = new JSONArray();
           for (int j = 0; j < maps[i].listPOI.size(); j++)
           {
               JSONObject poiData = new JSONObject();
               POI poi = maps[i].listPOI.get(j);
               poiData.put("name", poi.getName());
               poiData.put("type", categoryToString.get(poi.getType()));
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               poiData.put("isFavourite", poi.getFavouriteStatus());
               
               poiList.put(poiData);
           }
           
           alumni.put("LEVEL_" + floorLevel, poiList);
           floorLevel++;
        }
        
        buildings.put("ALUMNI", alumni);
        
        floorLevel = 2;
        for (int i = 3; i < 8; i++)
        {
             JSONArray poiList = new JSONArray();
           for (int j = 0; j < maps[i].listPOI.size(); j++)
           {
               JSONObject poiData = new JSONObject();
               POI poi = maps[i].listPOI.get(j);
               poiData.put("name", poi.getName());
               poiData.put("type", categoryToString.get(poi.getType()));
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               poiData.put("isFavourite", poi.getFavouriteStatus());
               
               poiList.put(poiData);
           }
           
           middlesex.put("LEVEL_" + floorLevel, poiList);
           floorLevel++;
        }
        
        buildings.put("MIDDLESEX", middlesex);
        
        floorLevel = 1;
        for (int i = 8; i < 13; i++)
        {
             JSONArray poiList = new JSONArray();
           for (int j = 0; j < maps[i].listPOI.size(); j++)
           {
               JSONObject poiData = new JSONObject();
               POI poi = maps[i].listPOI.get(j);
               poiData.put("name", poi.getName());
               poiData.put("type", categoryToString.get(poi.getType()));
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               poiData.put("isFavourite", poi.getFavouriteStatus());
               
               poiList.put(poiData);
           }
           
           ncb.put("LEVEL_" + floorLevel, poiList);
           floorLevel++;
        }
        
        buildings.put("NORTH_CAMPUS", ncb);
        
        //USERNAME AND PASSWORD FOR ADMIN
        JSONObject user = new JSONObject();
        user.put("username", "Admin");
        user.put("password", "123456");

        buildings.put("user", user);
        
        //URL obj = this.getClass().getClassLoader().getResource("data/app.json");
        //String path = "src/resources/data/app.json";
        try (FileWriter file = new FileWriter(path)) {
            file.write(buildings.toString());
            return true;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}