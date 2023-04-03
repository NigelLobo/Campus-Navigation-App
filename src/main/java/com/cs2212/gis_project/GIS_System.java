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
    private User currentUser = null;
    
    private GIS_System() {}
    
    public static GIS_System getInstance()
    {
        return s_Instance;
    }
    
    public Map[] Load()
    {
        try {
            JSONParser parser = new JSONParser();
            URL obj = this.getClass().getClassLoader().getResource("data/app.json");
            
            String contents = new String(Files.readAllBytes(Paths.get(obj.getFile())));
            JSONObject buildings = new JSONObject(contents);
            JSONObject alumni = buildings.getJSONObject("ALUMNI");
            JSONObject middlesex = buildings.getJSONObject("MIDDLESEX");
            JSONObject ncb = buildings.getJSONObject("NORTH_CAMPUS");
            
            Map[] maps = new Map[13];
            
            int floorLevel = 1;
            for (int i = 0; i < 3; i++)
            {
                JSONArray poiList = alumni.getJSONArray("LEVEL_" + floorLevel);
                ArrayList<POI> pois = new ArrayList<POI>();
                for (int j = 0; j < poiList.length(); j++)
                {
                    JSONObject currentPOI = poiList.getJSONObject(j);
                    POI poi = new POI((String)currentPOI.get("name"), (Category)currentPOI.get("type"), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"));
                    pois.add(poi);
                }
                maps[i] = new Map("ALUMNI_" + floorLevel);
                maps[i].setListPOI(pois);
                floorLevel++;
            }
            
            floorLevel = 1;
            for (int i = 3; i < 8; i++)
            {
                JSONArray poiList = middlesex.getJSONArray("LEVEL_" + floorLevel);
                ArrayList<POI> pois = new ArrayList<POI>();
                for (int j = 0; j < poiList.length(); j++)
                {
                    JSONObject currentPOI = poiList.getJSONObject(j);
                    POI poi = new POI((String)currentPOI.get("name"), (Category)currentPOI.get("type"), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"));
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
                    POI poi = new POI((String)currentPOI.get("name"), (Category)currentPOI.get("type"), (Integer)currentPOI.get("posX"), (Integer)currentPOI.get("posY"));
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
    
    public void Save(Map[] maps)
    {
        JSONObject buildings = new JSONObject();
        JSONObject alumni = new JSONObject();
        JSONObject middlesex = new JSONObject();
        JSONObject ncb = new JSONObject();
        
        int floorLevel = 1;
        for (int i = 0; i < 3; i++)
        {
            JSONArray poiList = new JSONArray();
           for (int j = 0; j < maps[i].listPOI.size(); j++)
           {
               JSONObject poiData = new JSONObject();
               POI poi = maps[i].listPOI.get(j);
               poiData.put("name", poi.getName());
               poiData.put("type", poi.getType());
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               
               poiList.put(poiData);
           }
           
           alumni.put("LEVEL_" + floorLevel, poiList);
           floorLevel++;
        }
        
        buildings.put("ALUMNI", alumni);
        
        floorLevel = 1;
        for (int i = 3; i < 8; i++)
        {
             JSONArray poiList = new JSONArray();
           for (int j = 0; j < maps[i].listPOI.size(); j++)
           {
               JSONObject poiData = new JSONObject();
               POI poi = maps[i].listPOI.get(j);
               poiData.put("name", poi.getName());
               poiData.put("type", poi.getType());
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               
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
               poiData.put("type", poi.getType());
               poiData.put("posX", poi.getPosition()[0]);
               poiData.put("posY", poi.getPosition()[1]);
               
               poiList.put(poiData);
           }
           
           ncb.put("LEVEL_" + floorLevel, poiList);
           floorLevel++;
        }
        
        buildings.put("NORTH_CAMPUS", ncb);
        
        /* NOT THE PROPER FILE PATH */
        URL obj = this.getClass().getClassLoader().getResource("data/app.json");
        try (FileWriter file = new FileWriter(obj.getFile())) {
            file.write(buildings.toString());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
