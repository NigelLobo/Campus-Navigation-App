package com.cs2212.gis_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Demo of using org.json.simple to read a json file.
 * 
 * This demo is taken from: https://www.digitalocean.com/community/tutorials/json-simple-example
 */
public class JSONDemo {    
	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException, URISyntaxException {
		JSONParser parser = new JSONParser();
                
                // Example of how to find files in the src/resources dir
                URL jsonFile = JSONDemo.class.getClassLoader().getResource("data/example.json");
		Reader reader = new InputStreamReader(jsonFile.openStream());

		Object jsonObj = parser.parse(reader);

		JSONObject jsonObject = (JSONObject) jsonObj;

		String name = (String) jsonObject.get("name");
		System.out.println("Name = " + name);

		long age = (Long) jsonObject.get("age");
		System.out.println("Age = " + age);

		JSONArray cities = (JSONArray) jsonObject.get("cities");
		
		@SuppressWarnings("unchecked")
		Iterator<String> it = cities.iterator();
		while (it.hasNext()) {
			System.out.println("City = " + it.next());
		}
		reader.close();
	}
}