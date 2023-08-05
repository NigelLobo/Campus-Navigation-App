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
 * This class handles fetching and returning weather data via API requests
 *
 * @author Daniel Hoang
 */
public class Weather {

    /**
     * HttpClient handles network requests
     */
    private HttpClient client = HttpClient.newHttpClient();

    /**
     * London's latitude value
     */
    private int londonLat;

    /**
     * London's Longitude value
     */
    private int londonLng;

    /**
     * JSONObject that will store the weather data requested
     */
    private JSONObject weatherAPIJSON;

    /**
     * Link to the Open Mateo API.
     */
    private final String url = "https://api.open-meteo.com/v1/forecast?latitude=42.98&longitude=-81.23&current_weather=true&daily=temperature_2m_max,temperature_2m_min&timezone=America%2FNew_York";

    /**
     * Stores the response from the GET request
     */
    private HttpResponse<String> response;

    /**
     * Stores content of the response
     */
    private String responsebody;

    /**
     * Denotes whether or not the internet is connected
     */
    public boolean internetIsOn;

    /**
     * Constructor for Weather class
     */
    public Weather() {
        internetIsOn = true;
    }

    /**
     * Sends an HTTP GET request to the specified URL with a JSON header and
     * returns the current temperature as a double value. If the response code
     * is not 200, a RuntimeException is thrown.
     *
     * @return the current temperature as a double value, or Double.MAX_VALUE if
     * the request fails
     */
    public double getTodaysTemp() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();
        try {
            //send request to API
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
            }
            responsebody = response.body();

            //parse the JSON response.body
            JSONParser parser = new JSONParser();
            weatherAPIJSON = (JSONObject) parser.parse(responsebody);

            //Extract the current weather information and todays temp
            JSONObject current = (JSONObject) weatherAPIJSON.get("current_weather");
            double current_temp = (double) current.get("temperature");
            return current_temp;
        } catch (Exception e) {
            System.out.println("Fetch Request Failed");
            return Double.MAX_VALUE;
        }
    }
}
