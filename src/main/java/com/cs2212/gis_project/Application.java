/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.cs2212.gis_project;

import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.ListSelectionModel;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;


/**
 * This class has functions critical to the application and handles all GUI
 * operations.
 *
 * @author nlobo9
 */
public class Application extends javax.swing.JFrame {
    private javax.swing.JLabel tempJLabel;
    private Point mousePos;
    private boolean creationMode, devMode, loadingFavs, loadingCurrent;
    private String activeMap;
    private User activeUser;
    private Map currMap;
    private String HELP_TEXT = "Abbreviations \n" +
        "NS - Natural Science\n" +
        "AH - Alumni Hall\n" +
        "MC - Middlesex College\n" +
        "\n" +
        "Getting Started \n" +
        "- select the building logo and click on the different buildings drop down menu to check the mapping of the different floors\n" +
        "- select the layers icon to toggle layers to make display only certain POIs to make it easier to search for what you want\n" +
        "- select the editing icon to create a POI, delete a POI, or edit a POI\n" +
        "\n" +
        "Custom POIs\n" +
        "- to create just click on an area of the map you want to create a POI of and enter the name of the POI and select the layer type\n" +
        "- to delete ...\n" +
        "- to edit ...\n" +
        "\n" +
        "About us:\n" +
        "We are a team consisting of 5 student developers who created group 42 western's GIS application in hopes of making navigating western buildings easier\n" +
        "Version : \n" +
        "Release Date : \n" +
        "\n" +
        "Contact us\n" +
        "Nigel Lobo @nlogo9@uwo.ca\n" +
        "Daniel Ngo @dhoang22@uwo.ca\n" +
        "Charmaine Lee @slee2769@uwo.ca\n" +
        "Rafay Kashif @rkashif3@uwo.ca\n" +
        "Sue Han @zhan246@uwo.ca";
        
//    private String[] mapFiles = { "maps/mc0.png", "maps/mc1.png", "maps/mc2.png", "maps/mc3.png", "maps/mc4.png"};
    //private Map activeMapObj;
    //private Map map = new Map("MIDDLESEX");
    private ArrayList<POI> currPoiList;
    private Map[] listOfMaps;
    private HashMap<String, int[]> poiNameToPos = new HashMap<>(); //key: poi name, value: (x,y) coords as array
    private HashMap<javax.swing.JLabel, POI> poiLabels = new HashMap<>(); //key: poi jlabel reference, value: POI obj reference
    private HashMap<Category, Boolean> activeLayers = new HashMap<>(); //key: Category enum type, value: true or false
    private HashMap<String, Map> maps = new HashMap<>(); //key: map name, value: map object
//    private HashMap<String, String> jsonMapToName = new HashMap<>();
    private GIS_System gis_system = GIS_System.getInstance();
    private javax.swing.JLabel arrow = new javax.swing.JLabel();

    /**
     * Creates new form GUI
     */
    public Application() {
        initComponents();
        Weather weather = new Weather();
        
        Double temp = weather.getTodaysTemp();
//                
        if (temp != Double.MAX_VALUE) {
            weatherLabel.setText(Double.toString(Math.round(temp)) + " °C");
        }
        
        buildingSelectPanel.setVisible(false);
        buildingPanel.setVisible(false);
        layerPanel.setVisible(false);
        customPanel.setVisible(false);
        mapImageScrollPane.setVisible(false);
        //activeMap = new Map(); dont do it like this access it from the hashmap
        loginFailLabel.setVisible(false);
        blackMenuPanel.setVisible(false);
        //set all layer types to active
        this.activeLayers.put(Category.CLASSROOM, true);
        this.activeLayers.put(Category.RESTAURANT, true);
        this.activeLayers.put(Category.LAB, true);
        this.activeLayers.put(Category.WASHROOM, true);
        this.activeLayers.put(Category.ELEVATOR, true);
        this.activeLayers.put(Category.CUSTOM, true);
    }

    /**
     * Start the application (after login).
     */
    public void start(String chosenBuilding) {
        arrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/arrow-pointing-down.png")));
        if (devMode) userModeLabel.setText("Admin Mode");
        else userModeLabel.setText("Guest Mode");
//        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
//        Component frameSelf = this;
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//                if (JOptionPane.showConfirmDialog(frameSelf, 
//                    "You have unsaved changes. ?", "Close Window?", 
//                    JOptionPane.YES_NO_OPTION,
//                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//                    System.exit(0);
//                }
//            }
//        });

        listOfMaps = gis_system.Load("src/resources/data/app.json"); //get all 13 maps loaded with the poi data
        for (Map m : listOfMaps) {
            maps.put(m.getName(), m);
            System.out.println("Loaded map: " + m.getName());
            ArrayList<POI> tempPOIList = m.getPOIList();
            for (POI p : tempPOIList) {
                int[] pos = p.getPosition();
                poiNameToPos.put(p.getName(), pos);
            }
        }
        
        System.out.println(chosenBuilding + " chosen...");
        buildingSelectPanel.setVisible(false);
        mapPanel.setVisible(true);
        mapImageScrollPane.setVisible(true);
        blackMenuPanel.setVisible(true);
        guiPOIList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        favList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        String defaultLevel = "";
        if (chosenBuilding.equals("MIDDLESEX COLLEGE")) {
            defaultLevel = "LEVEL_2.png";
            this.activeMap = "MIDDLESEX_2";
        }
        else if (chosenBuilding.equals("ALUMNI HALL")) {
            defaultLevel = "LEVEL_2.png";
            this.activeMap = "ALUMNI_2";
        }
        else {
            defaultLevel = "LEVEL_1.png";
            this.activeMap = "NORTH_CAMPUS_1";
        }
        
        //set map image for chosen building. Default to ground floor for each 
        String path = "/maps/" + chosenBuilding.toUpperCase() + "_" + defaultLevel;
        path = path.replaceAll(" ", "_");
        
        mapImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
        
        //display the appropriate pois... all layers selected by default
        classroomCheckbox.setSelected(true);
        washroomCheckbox.setSelected(true);
        labCheckbox.setSelected(true);
        restaurantCheckbox.setSelected(true);
        elevatorCheckbox.setSelected(true);
        customCheckbox.setSelected(true);
        
        loadPOIs(path);
        loadFavourites();
        
        
    }

    /**
     * Exit the application.
     */
    public void exit() {
        //TODO: need to somehow override the windowlister for this jframe. Cant right now beause netbeans doesnt allow me to edit the frame on exit action.
        //look into this...
        //check for unsaved changes
//        
//        if (unsavedChanges) {
//            JOptionPane.showMessageDialog(this, "You have unsaved changes.", "Unsaved Changes", JOptionPane.WARNING_MESSAGE);
//        }
    }
    
    public void loadCurrent(ArrayList<POI> list) {
        loadingCurrent = true;
        String[] names = new String[currPoiList.size()];
        for (int i = 0; i < currPoiList.size(); i++) {
            names[i] = currPoiList.get(i).getName();
        }
        guiPOIList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = names;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        mapImageLabel.repaint();
        guiPOIList.repaint();
        loadingCurrent = false;
    }
    
    public void loadFavourites() {
        //iterate through current displayed poi list and if favourite, add to favourite list
        loadingFavs = true;
        ArrayList<POI> listOfFavs = new ArrayList<>();
        for (Map m : listOfMaps) {
            ArrayList<POI> temp = m.getPOIList();
            for (POI p : temp) {
                if (p.getFavouriteStatus()) listOfFavs.add(p);
            }
        }
        
        String[] listOfFavourites = new String[listOfFavs.size()];
        for (int i = 0; i < listOfFavs.size(); i++) {
            listOfFavourites[i] = listOfFavs.get(i).getName();
        }
        favList.setModel(new javax.swing.AbstractListModel<String>() {
//            String[] strings = names; //SHOULD BE THIS
            String[] strings = listOfFavourites; 
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        favList.repaint();
        loadingFavs = false;
    }
    
    public void loadPOIs(String buildingFloorFilePath) {
        String buildingName = "";
        String floorNum = "";
        if (buildingFloorFilePath.contains("MIDDLESEX")) buildingName = "MIDDLESEX";
        else if (buildingFloorFilePath.contains("ALUMNI")) buildingName = "ALUMNI";
        else if (buildingFloorFilePath.contains("NORTH")) buildingName = "NORTH_CAMPUS";
        
        buildingFloorFilePath = buildingFloorFilePath.substring(0, buildingFloorFilePath.length() - 4);
        floorNum = buildingFloorFilePath.substring(buildingFloorFilePath.length() - 1, buildingFloorFilePath.length());
        this.activeMap = buildingName + "_" + floorNum;
        currMap = maps.get(buildingName + "_" + floorNum);
        currPoiList = currMap.getPOIList();
//          ArrayList<POI> poiList = currMap.getPOIList();
//        String[] names = new String[currPoiList.size()];
//        for (int i = 0; i < currPoiList.size(); i++) {
//            names[i] = currPoiList.get(i).getName();
//        }
//        guiPOIList.setModel(new javax.swing.AbstractListModel<String>() {
////            String[] strings = names; //SHOULD BE THIS
//            String[] strings = names; 
//            public int getSize() { return strings.length; }
//            public String getElementAt(int i) { return strings[i]; }
//        });
        loadCurrent(currPoiList);
        Component frameSelf = this;
//        POI[] poiList= {};// = map.getPOIList()
        for (POI p : currPoiList) {
            int[] pos = p.getPosition();
            javax.swing.JLabel somePOILabel = new javax.swing.JLabel();
            somePOILabel.setBounds(pos[0], pos[1],75,75);//CHANGE THE SCALE
            somePOILabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/poi.png")));
            mapImageLabel.add(somePOILabel,new Integer(10));
            poiLabels.put(somePOILabel, p);
            int[] coords = p.getPosition();
            poiNameToPos.put(p.getName(), coords);
            javax.swing.JLabel labelRef = somePOILabel;
            somePOILabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                  String pLayer = "";
                  switch(p.getType()) {
                    case CLASSROOM: pLayer = "Classroom";break;
                    case WASHROOM: pLayer = "Washroom";break;
                    case ELEVATOR: pLayer = "Elevator";break;
                    case CUSTOM: pLayer = "Custom";break;
                    case LAB: pLayer = "Lab";break;
                    case RESTAURANT: pLayer = "Restaurant";break;
                    default: break;

                  }
//                 // builtin menu (admin only)
                   if (devMode && !pLayer.equals("Custom")) {
                       JTextField builtinName = new JTextField(p.getName());
                       
                       JButton deleteButton = new JButton("Delete");
                        deleteButton.addActionListener(new ActionListener() {
                              @Override
                              public void actionPerformed(ActionEvent e) {
      //                            
                                    //delete POI entry from Map
                                    String poiName = poiLabels.get(labelRef).getName();
                                    currMap.deletePOI(poiName); 
                           currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                                    //delete JLabel entry
                                    poiLabels.remove(labelRef);
                                    
                                    mapImageLabel.repaint();
                                    mapImageLabel.remove(labelRef);
                                    gis_system.Save(listOfMaps,"src/resources/data/app.json");
                              }
                        });

                        JButton editButton = new JButton("Edit");
                        editButton.addActionListener(new ActionListener() {
                              @Override
                              public void actionPerformed(ActionEvent e) {
                                    //make a popup... get the data from the action listener...
                                    JTextField editName = new JTextField();
                                    String[] types = {"Classroom", "Restaurant", "Lab", "Washroom", "Elevator"};
                                    Category[] typesEnum = {Category.CLASSROOM, Category.RESTAURANT, Category.LAB, Category.WASHROOM, Category.ELEVATOR};
                                    JComboBox layerDropdown = new JComboBox(types);
                                    int result = JOptionPane.showOptionDialog(frameSelf, new Object[] {"Edit name", editName, "Edit Layer", layerDropdown, "Press OK to Save. Close all menus and click again to see results.", "Admin cannot set favourites."},
                          "Edit POI Information", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
                                    
                                 if (result == JOptionPane.OK_OPTION) {
                                     Category devType = typesEnum[layerDropdown.getSelectedIndex()];
                                     System.out.println(devType);
                                      //delete poi from map
                                      String poiName = poiLabels.get(labelRef).getName();
                                      currMap.deletePOI(poiName);
                                      //add poi back into map
                                      int[] pos = poiLabels.get(labelRef).getPosition();
                                      POI editedPOI = new POI(editName.getText(), devType, pos[0], pos[1], false);
                                      currMap.addPOI(editName.getText(), devType, pos[0], pos[1]);
                                      currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                                      poiLabels.remove(labelRef);
                                      poiLabels.put(labelRef, editedPOI);
                                      gis_system.Save(listOfMaps,"src/resources/data/app.json");
                                      mapImageLabel.repaint();
                                 }

                              }
                        });
                        highlightPOI(poiLabels.get(labelRef).getPosition()[0],poiLabels.get(labelRef).getPosition()[1]);
                        JOptionPane.showOptionDialog(frameSelf, new Object[] {"Name: " + poiLabels.get(labelRef).getName() + "\n Layer: " + pLayer + "\n", editButton, deleteButton, "Close Menu to see changes made from editing."},
                          "Builtin POI Information", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);


                   } 
                   //custom (admin and guest can edit)
                   else if (pLayer.equals("Custom")) { //custom menu for builtins, regular user
                       JTextField builtinName = new JTextField(p.getName());
                       
                       JButton deleteButton = new JButton("Delete");
                        deleteButton.addActionListener(new ActionListener() {
                              @Override
                              public void actionPerformed(ActionEvent e) {
      //                            
                                    //delete POI entry from Map
                                    String poiName = poiLabels.get(labelRef).getName();
                                    currMap.deletePOI(poiName); 
                                    currPoiList = currMap.getPOIList();
                                    loadCurrent(currPoiList);
                                    //delete JLabel entry
                                    poiLabels.remove(labelRef);
                                    mapImageLabel.repaint();
                                    mapImageLabel.remove(labelRef);
                                    gis_system.Save(listOfMaps,"src/resources/data/app.json");
                              }
                        });

                        JButton editButton = new JButton("Edit");
                        editButton.addActionListener(new ActionListener() {
                              @Override
                              public void actionPerformed(ActionEvent e) {
                                    //make a popup... get the data from the action listener...
                                    JTextField editName = new JTextField();
                                    int result = JOptionPane.showOptionDialog(frameSelf, new Object[] {"Edit name", editName, "Press OK to Save. Close all menus and click again to see results."},
                          "Edit Custom POI Information", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
                                    
                                 if (result == JOptionPane.OK_OPTION) {
                                 
                                      //delete poi from map
                                      String poiName = poiLabels.get(labelRef).getName();
                                      currMap.deletePOI(poiName);
                                      //add poi back into map
                                      int[] pos = poiLabels.get(labelRef).getPosition();
                                      POI editedPOI = new POI(editName.getText(), Category.CUSTOM, pos[0], pos[1], false);
                                      currMap.addPOI(editName.getText(), Category.CUSTOM, pos[0], pos[1]);
                                      currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
//                                      poiLabels.remove(labelRef);
                                      poiLabels.put(labelRef, editedPOI);
                                      gis_system.Save(listOfMaps,"src/resources/data/app.json");
                                 }

                              }
                        });
                        highlightPOI(poiLabels.get(labelRef).getPosition()[0],poiLabels.get(labelRef).getPosition()[1]);
                        JOptionPane.showOptionDialog(frameSelf, new Object[] {"Name: " + poiLabels.get(labelRef).getName() + "\n Layer: " + pLayer + "\n", editButton, deleteButton, "Close Menu to see changes made from editing."},
                          "Custom", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);

                   }
                   //builtin, guest
                   else {
                        JButton addFavButton = new JButton();
                        String favStatus;
                        if (poiLabels.get(labelRef).getFavouriteStatus() == true) favStatus = "Remove Favourite";
                        else favStatus = "Add Favourite";
                        addFavButton.setText(favStatus);
                        addFavButton.addActionListener(new ActionListener() {
                              @Override
                              public void actionPerformed(ActionEvent e) {
                                  String favStatus;
                                  if (poiLabels.get(labelRef).getFavouriteStatus() == true) favStatus = "Remove Favourite";
                                  else favStatus = "Add Favourite";
                                  if (favStatus.equals("Add Favourite")) {
                                      poiLabels.get(labelRef).setFavouriteStatus(true);
                                      addFavButton.setText("Remove Favourite");
                                      loadFavourites();
                                  }
                                  else {
                                      poiLabels.get(labelRef).setFavouriteStatus(false);
                                      addFavButton.setText("Add Favourite");
                                      loadFavourites();
                                  }
                                  System.out.println("Set to " + poiLabels.get(labelRef).getFavouriteStatus());
                                  addFavButton.repaint();
                                  gis_system.Save(listOfMaps,"src/resources/data/app.json");
                              }
                        });
                       highlightPOI(poiLabels.get(labelRef).getPosition()[0],poiLabels.get(labelRef).getPosition()[1]);
                       JOptionPane.showOptionDialog(frameSelf, new Object[] {"Name: " + poiLabels.get(labelRef).getName() + "\n Layer: " + pLayer + "\n", addFavButton},
                          "Builtin", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);

                   }
                }
            });
        }
//***************************************************************TO BE REMOVED
//        javax.swing.JLabel somePOILabel = new javax.swing.JLabel();
//            somePOILabel.setBounds(100,100,75,75);//CHANGE THE SCALE
//            somePOILabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/poi.png")));
//            mapImageLabel.add(somePOILabel,new Integer(10));
//            somePOILabel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                   JOptionPane.showMessageDialog(frameSelf, "poi info", "POI Information", JOptionPane.INFORMATION_MESSAGE);
//                }
//            });
//*************************************************************************************
//            
//            jLabel.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            jLabel.setIcon(newIcon);
//        }
//    });
            
//        for (Component c : mapImageLabel.getComponents()) {
//            System.out.println((c == somePOILabel));//same reference !
//            
//        }
//        repaint() 
    }
    
    /**
     * Finds the desired POI object from the current map.
     *
     * @param search name of the POI eg. "MC 105"
     * @return reference to desired POI object
     */
    public POI findPOI(String search) {
        return null;
    }
    /**
     * Changes the map that is being displayed.
     *
     * @param name
     */
    public void changeMap(String buildingName, String floorLevel) {
        //change the image icon for the JLabel representing the map image. 
        String filepath = "/maps/" + buildingName.toUpperCase() + "_" + floorLevel.toUpperCase();
        filepath = filepath.replaceAll(" ", "_") + ".png";
        System.out.println(filepath);
        mapImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(filepath)));
        
        //delete all current POIs, and display the new POIs for the current map also taking into account layer toggles...
        for (Component c : mapImageLabel.getComponents()) {
            mapImageLabel.remove(c);
            //clear hashmap
            
        }
        
        loadPOIs(filepath);
    }

    /**
     * Returns the currently displayed map name
     *
     * @return name of active map
     */
    public String getActiveMap() {
        return this.activeMap;
    }
    
    public void searchPOI(String poiName) {
        System.out.println("Searching for " + poiName);
        //iterate through each map, linear search with getPOI -> and center the viewport to it, switch map if needed
        for (Map m : listOfMaps) {
            POI p = m.getPOI(poiName);
            if (p != null) {
                //switch map if needed
                if (!this.getActiveMap().equals(m.getName())) {
                    String newMap = ""; 
                    String newFloor = "";
                    String oldMap = m.getName();
                    if (oldMap.contains("MIDDLESEX")) newMap = "MIDDLESEX COLLEGE";
                    else if (oldMap.contains("ALUMNI")) newMap = "ALUMNI HALL";
                    else if (oldMap.contains("NORTH_CAMPUS")) newMap = "NORTH CAMPUS BUILDING";
                    
                    newFloor = "LEVEL_" + oldMap.substring(oldMap.length() - 1, oldMap.length());
                    
                    changeMap(newMap, newFloor);
                }
                //center viewport to it
                int[] pos = p.getPosition();
//                mapImageScrollPane.getViewport().setViewPosition(new Point(pos[0], pos[1]));
                mapImageScrollPane.getViewport().setViewPosition(getIdealViewPos(pos[0], pos[1]));
                highlightPOI(pos[0], pos[1]);
                System.out.println("Found POI!");
                buildingPanel.setVisible(false);
                return;
            }
        }
        
        JOptionPane.showMessageDialog(this, "The POI you searched for could not be found.", "Could not find POI", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Alters the visibility of a POI layer
     *
     * @param type the group of layers to alter (Classroom, Lab, etc.)
     * @param active true to make visible, false to make invisible
     */
    public void toggleLayer(Category type, boolean active) {
        //update the activeLayers hashmap 
        this.activeLayers.put(type, active);
        System.out.println(type + " layer set to " + Boolean.toString(active));
        
        //repaint the map to only include active layers
        //for each poi image, sets its visibility according to the activeLayers hashmap
        for (Component c : mapImageLabel.getComponents()) {
            //get the current components category by accessing the <JLabel, POI> hashmap
            Category cType = poiLabels.get(c).getType();
            c.setVisible(this.activeLayers.get(cType));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginPanel = new javax.swing.JPanel();
        usernameTextField = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        passwordLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        loginPageLabel = new javax.swing.JLabel();
        loginFailLabel = new javax.swing.JLabel();
        westernLogoLabel = new javax.swing.JLabel();
        group42Label = new javax.swing.JLabel();
        guestModeButton = new javax.swing.JButton();
        buildingSelectPanel = new javax.swing.JPanel();
        selectBuildingLabel = new javax.swing.JLabel();
        mcButton = new javax.swing.JButton();
        mcLabel = new javax.swing.JLabel();
        ahButton = new javax.swing.JButton();
        ahLabel = new javax.swing.JLabel();
        ncbLabel = new javax.swing.JLabel();
        ncbButton = new javax.swing.JButton();
        westernLogo2 = new javax.swing.JLabel();
        mapPanel = new javax.swing.JPanel();
        blackMenuPanel = new javax.swing.JPanel();
        helpButton = new javax.swing.JButton();
        buildingMenuButton = new javax.swing.JButton();
        layersMenuButton = new javax.swing.JButton();
        customMenuButton = new javax.swing.JButton();
        weatherLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        guiPOIList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        favList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        userModeLabel = new javax.swing.JLabel();
        buildingPanel = new javax.swing.JPanel();
        buildingChangeLabel = new javax.swing.JLabel();
        closeBuilding = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        buildingTree = new javax.swing.JTree();
        layerPanel = new javax.swing.JPanel();
        layerSelectLabel = new javax.swing.JLabel();
        closeLayer = new javax.swing.JLabel();
        classroomCheckbox = new javax.swing.JCheckBox();
        restaurantCheckbox = new javax.swing.JCheckBox();
        labCheckbox = new javax.swing.JCheckBox();
        washroomCheckbox = new javax.swing.JCheckBox();
        elevatorCheckbox = new javax.swing.JCheckBox();
        customCheckbox = new javax.swing.JCheckBox();
        customPanel = new javax.swing.JPanel();
        customSelectLabel = new javax.swing.JLabel();
        closeCustom = new javax.swing.JLabel();
        createPOIButton = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        mapImageScrollPane = new javax.swing.JScrollPane();
        mapImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        usernameLabel.setText("Username");

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        passwordLabel.setText("Password");

        loginButton.setText("Admin Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        loginPageLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        loginPageLabel.setText("Login Page");

        loginFailLabel.setForeground(new java.awt.Color(255, 0, 0));
        loginFailLabel.setText("Username and/or password is incorrect. Try again.");

        westernLogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/westernLogo.png"))); // NOI18N

        group42Label.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        group42Label.setText("Group 42 - CS2212 GIS Project");

        guestModeButton.setText("Guest Mode");
        guestModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestModeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginPageLabel)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginFailLabel)
                            .addComponent(loginButton)
                            .addComponent(passwordLabel)
                            .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(usernameLabel)
                                .addComponent(usernameTextField)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(guestModeButton))
                        .addGap(116, 116, 116)
                        .addComponent(westernLogoLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(group42Label)
                .addGap(204, 204, 204))
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addComponent(loginPageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(group42Label)
                .addGap(99, 99, 99)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(passwordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginButton)
                        .addGap(62, 62, 62)
                        .addComponent(loginFailLabel))
                    .addComponent(westernLogoLabel))
                .addGap(97, 97, 97)
                .addComponent(guestModeButton)
                .addContainerGap(125, Short.MAX_VALUE))
        );

        selectBuildingLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        selectBuildingLabel.setText("Select a Building");

        mcButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        mcButton.setText("Middlesex College");
        mcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mcButtonActionPerformed(evt);
            }
        });

        mcLabel.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        mcLabel.setText("MC");

        ahButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ahButton.setText("Alumni Hall");
        ahButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ahButtonActionPerformed(evt);
            }
        });

        ahLabel.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        ahLabel.setText("AH");

        ncbLabel.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        ncbLabel.setText("NCB");

        ncbButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ncbButton.setText("North Campus Building");
        ncbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ncbButtonActionPerformed(evt);
            }
        });

        westernLogo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/westernLogo.png"))); // NOI18N

        javax.swing.GroupLayout buildingSelectPanelLayout = new javax.swing.GroupLayout(buildingSelectPanel);
        buildingSelectPanel.setLayout(buildingSelectPanelLayout);
        buildingSelectPanelLayout.setHorizontalGroup(
            buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectBuildingLabel)
                            .addComponent(mcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                                .addComponent(ahButton, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(ncbButton, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(westernLogo2)))
                    .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(mcLabel)
                        .addGap(287, 287, 287)
                        .addComponent(ahLabel)
                        .addGap(258, 258, 258)
                        .addComponent(ncbLabel)))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        buildingSelectPanelLayout.setVerticalGroup(
            buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buildingSelectPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(selectBuildingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buildingSelectPanelLayout.createSequentialGroup()
                        .addContainerGap(213, Short.MAX_VALUE)
                        .addComponent(westernLogo2)
                        .addGap(58, 58, 58)))
                .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ncbButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ahButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mcButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buildingSelectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ncbLabel)
                    .addComponent(ahLabel)
                    .addComponent(mcLabel))
                .addGap(259, 259, 259))
        );

        blackMenuPanel.setBackground(new java.awt.Color(0, 0, 0));

        helpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/help.png"))); // NOI18N
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                helpButtonMouseClicked(evt);
            }
        });
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        buildingMenuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/building.png"))); // NOI18N
        buildingMenuButton.setBorderPainted(false);
        buildingMenuButton.setContentAreaFilled(false);
        buildingMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildingMenuButtonActionPerformed(evt);
            }
        });

        layersMenuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/layers.png"))); // NOI18N
        layersMenuButton.setBorderPainted(false);
        layersMenuButton.setContentAreaFilled(false);
        layersMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layersMenuButtonActionPerformed(evt);
            }
        });

        customMenuButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/create.png"))); // NOI18N
        customMenuButton.setBorderPainted(false);
        customMenuButton.setContentAreaFilled(false);
        customMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customMenuButtonActionPerformed(evt);
            }
        });

        weatherLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        weatherLabel.setForeground(new java.awt.Color(255, 255, 255));
        weatherLabel.setText("No Internet");

        guiPOIList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        guiPOIList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                guiPOIListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(guiPOIList);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search POI");

        favList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        favList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                favListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(favList);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Favourites");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Current POI's");

        searchButton.setBackground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Find");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        userModeLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userModeLabel.setForeground(new java.awt.Color(255, 255, 255));
        userModeLabel.setText("N/A");

        javax.swing.GroupLayout blackMenuPanelLayout = new javax.swing.GroupLayout(blackMenuPanel);
        blackMenuPanel.setLayout(blackMenuPanelLayout);
        blackMenuPanelLayout.setHorizontalGroup(
            blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blackMenuPanelLayout.createSequentialGroup()
                .addGroup(blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(blackMenuPanelLayout.createSequentialGroup()
                        .addGroup(blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(helpButton)
                            .addGroup(blackMenuPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buildingMenuButton)
                                    .addComponent(layersMenuButton)
                                    .addComponent(customMenuButton)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(blackMenuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane3)
                            .addComponent(searchField)
                            .addGroup(blackMenuPanelLayout.createSequentialGroup()
                                .addGroup(blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(weatherLabel)
                                    .addComponent(searchButton)
                                    .addComponent(userModeLabel))
                                .addGap(0, 19, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        blackMenuPanelLayout.setVerticalGroup(
            blackMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, blackMenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buildingMenuButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layersMenuButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customMenuButton)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(helpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weatherLabel)
                .addGap(18, 18, 18)
                .addComponent(userModeLabel)
                .addGap(12, 12, 12))
        );

        buildingPanel.setBackground(new java.awt.Color(204, 204, 204));

        buildingChangeLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buildingChangeLabel.setText("Buildings");
        buildingChangeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buildingChangeLabelMouseClicked(evt);
            }
        });

        closeBuilding.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icons8-close-window-16.png"))); // NOI18N
        closeBuilding.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBuildingMouseClicked(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Middlesex College");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 3");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 4");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 5");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 6");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Alumni Hall");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 3");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 4");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("North Campus Building");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 3");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 4");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Level 5");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        buildingTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        buildingTree.setRootVisible(false);
        buildingTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buildingTreeMouseClicked(evt);
            }
        });
        buildingTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                buildingTreeValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(buildingTree);

        javax.swing.GroupLayout buildingPanelLayout = new javax.swing.GroupLayout(buildingPanel);
        buildingPanel.setLayout(buildingPanelLayout);
        buildingPanelLayout.setHorizontalGroup(
            buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buildingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buildingPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(buildingPanelLayout.createSequentialGroup()
                        .addComponent(buildingChangeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeBuilding))))
        );
        buildingPanelLayout.setVerticalGroup(
            buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buildingPanelLayout.createSequentialGroup()
                .addGroup(buildingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buildingPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buildingChangeLabel))
                    .addComponent(closeBuilding))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layerPanel.setBackground(new java.awt.Color(204, 204, 204));

        layerSelectLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        layerSelectLabel.setText("Layers");

        closeLayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icons8-close-window-16.png"))); // NOI18N
        closeLayer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLayerMouseClicked(evt);
            }
        });

        classroomCheckbox.setText("Classroom");
        classroomCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classroomCheckboxActionPerformed(evt);
            }
        });

        restaurantCheckbox.setText("Restaurant");
        restaurantCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaurantCheckboxActionPerformed(evt);
            }
        });

        labCheckbox.setText("Lab");
        labCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labCheckboxActionPerformed(evt);
            }
        });

        washroomCheckbox.setText("Washroom");
        washroomCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                washroomCheckboxActionPerformed(evt);
            }
        });

        elevatorCheckbox.setText("Elevator");
        elevatorCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevatorCheckboxActionPerformed(evt);
            }
        });

        customCheckbox.setText("Custom");
        customCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layerPanelLayout = new javax.swing.GroupLayout(layerPanel);
        layerPanel.setLayout(layerPanelLayout);
        layerPanelLayout.setHorizontalGroup(
            layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layerPanelLayout.createSequentialGroup()
                        .addComponent(layerSelectLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeLayer))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layerPanelLayout.createSequentialGroup()
                        .addGroup(layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(elevatorCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(washroomCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(restaurantCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(classroomCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customCheckbox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layerPanelLayout.setVerticalGroup(
            layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerPanelLayout.createSequentialGroup()
                .addGroup(layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(layerSelectLabel))
                    .addComponent(closeLayer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(classroomCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(restaurantCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(washroomCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(elevatorCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customCheckbox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customPanel.setBackground(new java.awt.Color(204, 204, 204));

        customSelectLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        customSelectLabel.setText("Custom");

        closeCustom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icons8-close-window-16.png"))); // NOI18N
        closeCustom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeCustomMouseClicked(evt);
            }
        });

        createPOIButton.setText("Create POI");
        createPOIButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPOIButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customPanelLayout = new javax.swing.GroupLayout(customPanel);
        customPanel.setLayout(customPanelLayout);
        customPanelLayout.setHorizontalGroup(
            customPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customSelectLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeCustom))
            .addComponent(createPOIButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        customPanelLayout.setVerticalGroup(
            customPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customPanelLayout.createSequentialGroup()
                .addGroup(customPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(customSelectLabel))
                    .addComponent(closeCustom))
                .addGap(18, 18, 18)
                .addComponent(createPOIButton)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        mapImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/maps/MIDDLESEX_COLLEGE_LEVEL_2.png"))); // NOI18N
        mapImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mapImageLabelMouseClicked(evt);
            }
        });
        mapImageScrollPane.setViewportView(mapImageLabel);

        jLayeredPane1.setLayer(mapImageScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mapImageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mapImageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapPanelLayout.createSequentialGroup()
                .addComponent(blackMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buildingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(layerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(blackMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mapPanelLayout.createSequentialGroup()
                .addComponent(buildingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(customPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLayeredPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(buildingSelectPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mapPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(buildingSelectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        loginFailLabel.setVisible(false);
        System.out.println("Attempting to login...");
        System.out.println(usernameTextField.getText());
        System.out.println(String.valueOf(passwordField.getPassword()));

        // login with user's credentials, else handle login failure
//        if (gis_system.login(usernameTextField.getText(), String.valueOf(passwordField.getPassword()))) {
          if (gis_system.login("src/resources/data/app.json", usernameTextField.getText(), String.valueOf(passwordField.getPassword()))) {
            System.out.println("Admin/Dev Mode Login successful.");
            //login success. Remove login page components and move to application UI
            for (Component c : loginPanel.getComponents()) {
                loginPanel.remove(c);
            }
            loginPanel.repaint();
            loginPanel.setVisible(false);
            buildingSelectPanel.setVisible(true);
            devMode = true;
        } else {
            System.out.println("Admin Login Failed.");
            loginFailLabel.setVisible(true);
        }

    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void mcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mcButtonActionPerformed
        // TODO add your handling code here:
        start("MIDDLESEX COLLEGE");
    }//GEN-LAST:event_mcButtonActionPerformed

    private void ahButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ahButtonActionPerformed
        // TODO add your handling code here:
        start("ALUMNI HALL");
    }//GEN-LAST:event_ahButtonActionPerformed

    private void ncbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ncbButtonActionPerformed
        // TODO add your handling code here:
        start("NORTH CAMPUS BUILDING");
    }//GEN-LAST:event_ncbButtonActionPerformed

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Help Screen");
    }//GEN-LAST:event_helpButtonActionPerformed

    private void buildingMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildingMenuButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Building");
        buildingPanel.setVisible(true);
        layerPanel.setVisible(false);
        customPanel.setVisible(false);
    }//GEN-LAST:event_buildingMenuButtonActionPerformed

    private void layersMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layersMenuButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Layer");        
        buildingPanel.setVisible(false);
        layerPanel.setVisible(true);
        customPanel.setVisible(false);

    }//GEN-LAST:event_layersMenuButtonActionPerformed

    private void customMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customMenuButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Create");
        buildingPanel.setVisible(false);
        layerPanel.setVisible(false);
        customPanel.setVisible(true);

    }//GEN-LAST:event_customMenuButtonActionPerformed

    private void closeBuildingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBuildingMouseClicked
        // TODO add your handling code here:
        buildingPanel.setVisible(false);
        layerPanel.setVisible(false);
        customPanel.setVisible(false);
    }//GEN-LAST:event_closeBuildingMouseClicked

    private void closeLayerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLayerMouseClicked
        // TODO add your handling code here:
        buildingPanel.setVisible(false);
        layerPanel.setVisible(false);
        customPanel.setVisible(false);
    }//GEN-LAST:event_closeLayerMouseClicked

    private void closeCustomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeCustomMouseClicked
        // TODO add your handling code here:        
        buildingPanel.setVisible(false);
        layerPanel.setVisible(false);
        customPanel.setVisible(false);
    }//GEN-LAST:event_closeCustomMouseClicked

    private void buildingTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buildingTreeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_buildingTreeMouseClicked
    
    private Point getIdealViewPos(int x, int y) {
        //split the map into 4 quadrants,
        //check which quadrant the poi lies in
        //return desired quadrant as a Point object
        Dimension dim = mapImageLabel.getSize();
        if (x <= dim.width / 2 && y <= dim.height / 2) return new Point(0,0);
        else if (x <= dim.width / 2 && y > dim.height / 2) return new Point(0, dim.height / 2);
        else if (x > dim.width / 2 && y > dim.height / 2) return new Point(dim.width / 2, dim.height / 2);
        else return new Point(dim.width / 2, 0);
        
    }
    
    private void buildingTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_buildingTreeValueChanged
        // TODO add your handling code here:
        //get selected node from Building JTree
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) buildingTree.getLastSelectedPathComponent();
        
        //change map to desired building and floor
        String buildingName = node.getParent().toString();
        String floorLevel = node.toString();
        //only change map if a floor was selected as well
        if (!buildingName.equalsIgnoreCase("root")) {
            changeMap(buildingName, floorLevel);
            System.out.println("Changed map to " + buildingName + " " + floorLevel);
        }
        
    }//GEN-LAST:event_buildingTreeValueChanged

    private void helpButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_helpButtonMouseClicked
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, this.HELP_TEXT, "Help", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_helpButtonMouseClicked

    private void classroomCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classroomCheckboxActionPerformed
        // TODO add your handling code here:
        toggleLayer(Category.CLASSROOM, classroomCheckbox.isSelected());
    }//GEN-LAST:event_classroomCheckboxActionPerformed

    private void restaurantCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restaurantCheckboxActionPerformed
        // TODO add your handling code here:
        toggleLayer(Category.RESTAURANT, restaurantCheckbox.isSelected());
    }//GEN-LAST:event_restaurantCheckboxActionPerformed

    private void labCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labCheckboxActionPerformed
        // TODO add your handling code here:
        toggleLayer(Category.LAB, labCheckbox.isSelected());
    }//GEN-LAST:event_labCheckboxActionPerformed

    private void washroomCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_washroomCheckboxActionPerformed
        // TODO add your handling code here:
        toggleLayer(Category.WASHROOM, washroomCheckbox.isSelected());
    }//GEN-LAST:event_washroomCheckboxActionPerformed

    private void elevatorCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elevatorCheckboxActionPerformed
        // TODO add your handling code here:
        toggleLayer(Category.ELEVATOR, elevatorCheckbox.isSelected());
    }//GEN-LAST:event_elevatorCheckboxActionPerformed

    private void createPOIButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPOIButtonActionPerformed
        this.creationMode = true;
        System.out.println(this.creationMode + " creation mode");
//        this.editMode = false;
//        this.deleteMode = false;
    }//GEN-LAST:event_createPOIButtonActionPerformed

    private void buildingChangeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buildingChangeLabelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_buildingChangeLabelMouseClicked

    private void guiPOIListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_guiPOIListValueChanged
        // TODO add your handling code here:
        if (!guiPOIList.getValueIsAdjusting() && !loadingCurrent) {
            System.out.println(guiPOIList.getSelectedValue() + " was selected.");
            //center map to selected poi
            String name = guiPOIList.getSelectedValue();
            int[] pos = poiNameToPos.get(name);
            //center scroll pane to currently selected poi
//            mapImageScrollPane.getViewport().setViewPosition(new Point(pos[0], pos[1]));
            mapImageScrollPane.getViewport().setViewPosition(getIdealViewPos(pos[0], pos[1]));
            highlightPOI(pos[0], pos[1]);
        }
        
    }//GEN-LAST:event_guiPOIListValueChanged

    private void favListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_favListValueChanged
        // TODO add your handling code here:
        if (!favList.getValueIsAdjusting() && !loadingFavs) {
            System.out.println(favList.getSelectedValue() + " was selected from the favourites menu.");
            //center map to selected poi
            String name = favList.getSelectedValue();
            searchPOI(name);
//            int[] pos = poiNameToPos.get(name);
//            //center scroll pane to currently selected poi
//            mapImageScrollPane.getViewport().setViewPosition(new Point(pos[0], pos[1] ));
        }
    }//GEN-LAST:event_favListValueChanged

    private void guestModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestModeButtonActionPerformed
        loginFailLabel.setVisible(false);
        System.out.println("Guest Mode successful.");
        for (Component c : loginPanel.getComponents()) {
            loginPanel.remove(c);
        }
        loginPanel.repaint();
        loginPanel.setVisible(false);
        buildingSelectPanel.setVisible(true);

        
    }//GEN-LAST:event_guestModeButtonActionPerformed

    private void customCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customCheckboxActionPerformed
        toggleLayer(Category.CUSTOM, customCheckbox.isSelected());
    }//GEN-LAST:event_customCheckboxActionPerformed
    
    /**
     * Centers POI and inserts arrow image above POI
     * @param x the x coordinate of poi
     * @param y the y coordinate of poi
     */
    public void highlightPOI(int x, int y) {
        arrow.setVisible(true);
        y = y - 50;
        x = x + 20;
        arrow.setBounds(x,y,30,30);
        mapImageLabel.add(arrow, new Integer(0));
        mapImageLabel.repaint();
    }
    
    private void mapImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mapImageLabelMouseClicked
        // TODO add your handling code here:
        if (!this.creationMode) return;
        this.mousePos = mapImageLabel.getMousePosition();
        System.out.println(evt.getX());
                System.out.println(evt.getY());
        javax.swing.JLabel somePOILabel = null;
        Component frameSelf = this;
//        if (this.creationMode) {
            this.creationMode = false;
            //create a new POI and show popup
            somePOILabel = new javax.swing.JLabel();
            somePOILabel.setBounds(evt.getX() - 50,evt.getY() - 50,75,75);//CHANGE THE SCALE
            somePOILabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/poi.png")));
            mapImageLabel.add(somePOILabel,new Integer(10));
            tempJLabel = somePOILabel;
            somePOILabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                  String pLayer = "";
                  switch(poiLabels.get(tempJLabel).getType()) {
                    case CLASSROOM: pLayer = "Classroom"; break;
                    case WASHROOM: pLayer = "Washroom"; break;
                    case ELEVATOR: pLayer = "Elevator"; break;
                    case CUSTOM: pLayer = "Custom"; break;
                    case LAB: pLayer = "Lab"; break;
                    case RESTAURANT: pLayer = "Restaurant"; break;
                    default: break;
                  }
                  JButton addFavButton = new JButton();
                  String favStatus;
                  if (poiLabels.get(tempJLabel).getFavouriteStatus() == true) favStatus = "Remove Favourite";
                  else favStatus = "Add Favourite";
                  addFavButton.setText(favStatus);
                  addFavButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String favStatus;
                            if (poiLabels.get(tempJLabel).getFavouriteStatus() == true) favStatus = "Remove Favourite";
                            else favStatus = "Add Favourite";
                            if (favStatus.equals("Add Favourite")) {
                                poiLabels.get(tempJLabel).setFavouriteStatus(true);
                                addFavButton.setText("Remove Favourite");
                                loadFavourites();
                            }
                            else {
                                poiLabels.get(tempJLabel).setFavouriteStatus(false);
                                addFavButton.setText("Add Favourite");
                                loadFavourites();
                            }
                            System.out.println("Set to " + poiLabels.get(tempJLabel).getFavouriteStatus());
                            addFavButton.repaint();
                            gis_system.Save(listOfMaps,"src/resources/data/app.json");
                        }
                  });
                  JButton deleteButton = new JButton("Delete");
                  deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
//                            
                              //delete POI entry from Map
                              String poiName = poiLabels.get(tempJLabel).getName();
                              currMap.deletePOI(poiName); 
                               currPoiList = currMap.getPOIList();
                               loadCurrent(currPoiList);
                              //delete JLabel entry
                              poiLabels.remove(tempJLabel);
                              mapImageLabel.repaint();
                              mapImageLabel.remove(tempJLabel);
                              gis_system.Save(listOfMaps,"src/resources/data/app.json");
                        }
                  });
                  
                  JButton editButton = new JButton("Edit");
                  editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                              //make a popup... get the data from the action listener...
                              JTextField editName = new JTextField();
                              String[] types = {"Classroom", "Restaurant", "Lab", "Washroom", "Elevator"};
                              Category[] typesEnum = {Category.CLASSROOM, Category.RESTAURANT, Category.LAB, Category.WASHROOM, Category.ELEVATOR};
                              JComboBox layerDropdown = new JComboBox(types);
                              int result = JOptionPane.showOptionDialog(frameSelf, new Object[] {"Edit name", editName, "Edit Layer", "Click OK to Save. Close all menus to see results of edits made."},
                    "Edit POI Information", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
                              Category devType = typesEnum[layerDropdown.getSelectedIndex()];
                           if (result == JOptionPane.OK_OPTION) {
                                //delete poi from map
                                String poiName = poiLabels.get(tempJLabel).getName();
                                currMap.deletePOI(poiName);
                                                           currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                                //add poi back into map
                                int[] pos = poiLabels.get(tempJLabel).getPosition();
                                POI editedPOI = new POI(editName.getText(), devType, pos[0], pos[1], false);
                                currMap.addPOI(editName.getText(), devType, pos[0], pos[1]);
                                                           currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                                poiLabels.remove(tempJLabel);
                                poiLabels.put(tempJLabel, editedPOI);
                                gis_system.Save(listOfMaps,"src/resources/data/app.json");
                           }
                           
                        }
                  });
                  highlightPOI(poiLabels.get(tempJLabel).getPosition()[0],poiLabels.get(tempJLabel).getPosition()[1]);
                  JOptionPane.showOptionDialog(frameSelf, new Object[] {"Name: " + poiLabels.get(tempJLabel).getName() + "\n Layer: " + pLayer + "\n", addFavButton, editButton, deleteButton, "Click OK to Save. Close all menus to see results of edits made.",  "Admin cannot set favourites."},
                    "POI Information", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, null, null);
                  
//                   JOptionPane.showMessageDialog(frameSelf, "Name: " + poiLabels.get(tempJLabel).getName() + "\n Layer: " + pLayer + "\n", "POI Information", JOptionPane.INFORMATION_MESSAGE);
                }
            });
//        }
        mapImageLabel.repaint();
        if (devMode) {
            //allow admin to add POIS of all Types
            String[] types = {"Classroom", "Restaurant", "Lab", "Washroom", "Elevator"};
            Category[] typesEnum = {Category.CLASSROOM, Category.RESTAURANT, Category.LAB, Category.WASHROOM, Category.ELEVATOR};
            JComboBox layerDropdown = new JComboBox(types);
            JTextField nameField = new JTextField();
            int result = JOptionPane.showOptionDialog(this, new Object[] {"Enter POI Name:", nameField, "Enter Layer Type:", layerDropdown},
          "Create POI in Admin Mode", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (result == JOptionPane.OK_OPTION) {
                //make a new POI
                Category devType = typesEnum[layerDropdown.getSelectedIndex()];
                POI devPOI = new POI(nameField.getText(), devType, evt.getX(), evt.getY(), false);
                System.out.println(currMap);
                currMap.addPOI(nameField.getText(), devType, evt.getX(), evt.getY());
                                           currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                //add to hashmap
                int[] coord = {evt.getX(), evt.getY()};
                this.poiLabels.put(somePOILabel, devPOI);
                this.poiNameToPos.put(nameField.getText(), coord);
                System.out.println(this.poiLabels.get(somePOILabel).getName());
                
                //save to file
                gis_system.Save(listOfMaps, "src/resources/data/app.json");
            }
            else if (result == JOptionPane.CANCEL_OPTION) {
                mapImageLabel.remove(somePOILabel);
                mapImageLabel.repaint();
            }
        }
        else {
            JTextField nameField = new JTextField();
            int result = JOptionPane.showOptionDialog(this, new Object[] {"Enter POI Name:", nameField},
          "Create POI", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (result == JOptionPane.OK_OPTION) {
                //make a new POI
                POI newPOI = new POI(nameField.getText(), Category.CUSTOM, evt.getX(), evt.getY(), false);
                currMap.addPOI(nameField.getText(), Category.CUSTOM, evt.getX(), evt.getY());
                                           currPoiList = currMap.getPOIList();
                                      loadCurrent(currPoiList);
                //add to hashmap
                int[] coord = {evt.getX(), evt.getY()};
                this.poiLabels.put(somePOILabel, newPOI);
                this.poiNameToPos.put(nameField.getText(), coord);
                System.out.println(this.poiLabels.get(somePOILabel).getName());
                
                //save to file
                gis_system.Save(listOfMaps, "src/resources/data/app.json");
            }
            else if (result == JOptionPane.CANCEL_OPTION) {
                mapImageLabel.remove(somePOILabel);
                mapImageLabel.repaint();
            }
        }
    }//GEN-LAST:event_mapImageLabelMouseClicked

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchPOI(searchField.getText());
        searchField.setText("");
    }//GEN-LAST:event_searchButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ahButton;
    private javax.swing.JLabel ahLabel;
    private javax.swing.JPanel blackMenuPanel;
    private javax.swing.JLabel buildingChangeLabel;
    private javax.swing.JButton buildingMenuButton;
    private javax.swing.JPanel buildingPanel;
    private javax.swing.JPanel buildingSelectPanel;
    private javax.swing.JTree buildingTree;
    private javax.swing.JCheckBox classroomCheckbox;
    private javax.swing.JLabel closeBuilding;
    private javax.swing.JLabel closeCustom;
    private javax.swing.JLabel closeLayer;
    private javax.swing.JButton createPOIButton;
    private javax.swing.JCheckBox customCheckbox;
    private javax.swing.JButton customMenuButton;
    private javax.swing.JPanel customPanel;
    private javax.swing.JLabel customSelectLabel;
    private javax.swing.JCheckBox elevatorCheckbox;
    private javax.swing.JList<String> favList;
    private javax.swing.JLabel group42Label;
    private javax.swing.JButton guestModeButton;
    private javax.swing.JList<String> guiPOIList;
    private javax.swing.JButton helpButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox labCheckbox;
    private javax.swing.JPanel layerPanel;
    private javax.swing.JLabel layerSelectLabel;
    private javax.swing.JButton layersMenuButton;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginFailLabel;
    private javax.swing.JLabel loginPageLabel;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel mapImageLabel;
    private javax.swing.JScrollPane mapImageScrollPane;
    private javax.swing.JPanel mapPanel;
    private javax.swing.JButton mcButton;
    private javax.swing.JLabel mcLabel;
    private javax.swing.JButton ncbButton;
    private javax.swing.JLabel ncbLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JCheckBox restaurantCheckbox;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel selectBuildingLabel;
    private javax.swing.JLabel userModeLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private javax.swing.JCheckBox washroomCheckbox;
    private javax.swing.JLabel weatherLabel;
    private javax.swing.JLabel westernLogo2;
    private javax.swing.JLabel westernLogoLabel;
    // End of variables declaration//GEN-END:variables
}
