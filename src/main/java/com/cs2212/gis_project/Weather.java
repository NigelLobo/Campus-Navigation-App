/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cs2212.gis_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Daniel Hoang
 */
public class Weather {

    
    HttpClient client = HttpClient.newHttpClient();
    int londonLat;
    int londonLng;
    JSONObject weatherAPIJSON;
    String url = "https://api.open-meteo.com/v1/forecast?latitude=42.98&longitude=-81.23&daily=temperature_2m_max,temperature_2m_min&timezone=America%2FNew_York";
    HttpResponse<String> response;
    String responsebody;
    public Weather() {

    }

    public String getInstance(){
                HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
                try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
            }
            responsebody = response.body();
            return responsebody;
        } catch (Exception e) {
            System.out.println("Pull Request Failed");

        }
           return "Pull Request Succeeded";     
    }
    
    public JSONObject getWeather(){
        JSONParser parser = new JSONParser();
        try{
        weatherAPIJSON = (JSONObject) parser.parse(responsebody); 
        return weatherAPIJSON;
        } 
        catch (Exception e){
            System.out.println("Unable to access internet");
            return null;
        }
    }
    

}