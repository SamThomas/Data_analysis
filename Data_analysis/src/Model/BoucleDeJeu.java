//Version perso
package Model;

import View.Login;

// Green Planet libraries
import greenplanetclient.*;
import java.awt.BorderLayout;
import java.awt.Color;

// AWT librairies
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SWING libraries
import javax.swing.*;

// Standard libraries
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Constructor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import javax.xml.bind.JAXBException;
import java.sql.*; // library for da base connection


/**
 * JFreeChart Libraries
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

/**
 *
 * @author Samuel
 */
public class BoucleDeJeu {

    public BoucleDeJeu obj;
    private int typeIA;
    

    public void launch(ClientInterface client, String nomJoueur) throws IOException, JAXBException, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Memoire mem = new Memoire();
        Login login=new Login();
        
        XYSeries water_series = new XYSeries("Water");
        XYSeries light_series = new XYSeries("Light");
        XYSeries wind_series = new XYSeries("Wind");
        
        typeIA = login.getChoix();


        try {
            // String nomJoueur = "KingOfPop";

            // wait for game to start
            Game game = client.waitForStart();

            // while game is running
            while (game.getState() == GameStateEnum.RUNNING) {
                // display some stufs
                System.out.println("Play turn #" + game.getTurn());

                //Super Affichage des informations pour que vous sachiez comment les récupérer sans perdre 3 heures
                affichageInformations(game, nomJoueur);

                // compute order (AI job)

                ArrayList<String> liste_building = liste_building(game, nomJoueur,typeIA);
                Order order = new Order(nrjAacheter(game, nomJoueur, liste_building, mem), liste_building);
                game = client.giveOrder(order);

                double water = game.getWater();
                double light = game.getLight();
                double wind = game.getWind();

                water_series.add(game.getTurn(), water);
                light_series.add(game.getTurn(), light);
                wind_series.add(game.getTurn(), wind);
                
                //Thread.sleep(2000);


            }
            /**
             * ****************************************************************************************************************************************************
             */
            //Declaration & Initialisation
            //Declaration & Initialisation
            float nb_nuclear = 0;
            float nb_wind_turbines = 0;
            float nb_water_turbines = 0;
            float nb_coal_fired_plant = 0;
            float nb_solar_plant = 0;           
            float[] patrimoine; // Correspond at the total amount of money for each player
            patrimoine = new float[game.getPlayers().size()];  
            String[] playerName; // Tab with all the players name
            playerName = new String[game.getPlayers().size()];
            float[] cashJoueur; // Tab with all the amount of cah from each player
            cashJoueur = new float[game.getPlayers().size()];
            float[] patrimoine_buildings; // amount of money corresponding to all the buildings from each player
            patrimoine_buildings = new float[game.getPlayers().size()];


            // Data acquisition for each players
            for (int i = 0; i < game.getPlayers().size(); i++) {
                for (int j = 0; j < game.getPlayers().get(i).getBuildings().size(); j++) {

                    switch (game.getPlayers().get(i).getBuildings().get(j).getType()) {
                        case "nuclear":
                            nb_nuclear++; // increase the counter for the number of nuclear buildings
                            break;
                        case "wind_turbine":
                            nb_wind_turbines++; // increase the counter for the number of wind turbines buildings
                            break;
                        case "water_turbines":
                            nb_water_turbines++; // increase the counter for the number of water turbines buildings
                            break;
                        case "coal_fired_plant":
                            nb_coal_fired_plant++; // increase the counter for the number of the coal_fired_plant buildings
                            break;
                        case "solar_plant":
                            nb_solar_plant++; // increase the counter of the number of the solar_plant
                            break;
                    }
                }
                // Calculate the capital related to the buildings only (for each players)
                patrimoine_buildings[i] = nb_nuclear * 1000 + nb_wind_turbines * 100 + nb_solar_plant * 200 + nb_water_turbines * 500 + nb_coal_fired_plant * 400;
                cashJoueur[i] = game.getPlayers().get(i).getCash(); // get the amount of cash for the each player
            }


            // create the dataset for the capital chart
            DefaultPieDataset dataset = new DefaultPieDataset();

            for (int i = 0; i < game.getPlayers().size(); i++) {
                playerName[i] = game.getPlayers().get(i).getName(); // Get the name for all the players
                patrimoine[i] = cashJoueur[i] + patrimoine_buildings[i]; // Calculate the total "patrimoine"
                dataset.setValue("" + playerName[i], patrimoine[i]); // Set the dataset
            }
            
            // Capital chart creation
            JFreeChart capital_chart;
            capital_chart = ChartFactory.createPieChart3D(
                    "Capital chart", // title 
                    dataset,
                    false, //legend 
                    false,
                    false //urls
                    );

            ChartFrame capital_frame = new ChartFrame("Capital", capital_chart);
            
            // Get the size of the capital frame for a potential use in window organisation
            int capital_width = capital_frame.getContentPane().getWidth();
            int capital_height = capital_frame.getContentPane().getHeight();
            
            
            
            
            

            /****** METEO CHART********/

            // Create the meteo real-time based chart                
            XYSeriesCollection line_chart_data = new XYSeriesCollection();
            line_chart_data.addSeries(water_series);
            line_chart_data.addSeries(light_series);
            line_chart_data.addSeries(wind_series);

            JFreeChart meteo_chart = ChartFactory.createXYLineChart("meteo chart", //chart title
                    "tour", // x axis
                    "percentage", // y axis
                    line_chart_data, //data
                    PlotOrientation.VERTICAL,
                    true, // legend
                    true, // tooltips
                    true //urls
                    );

            ChartFrame meteo_frame = new ChartFrame("Meteo", meteo_chart);

            //   meteo_frame.pack();
            //  meteo_frame.setVisible(true);
            // frame2.repaint();
            

            //Window organisation
            JFrame second_window; 
            second_window = new JFrame();
            JPanel high_part;
            high_part = new JPanel();
            JPanel low_part;
            low_part = new JPanel();

            ChartPanel meteo_panel = new ChartPanel(meteo_chart);
            //   meteo_panel.setPreferredSize(new java.awt.Dimension(1300, 270));
            ChartPanel capital_panel = new ChartPanel(capital_chart);
            //  capital_panel.setPreferredSize(new java.awt.Dimension(500, 300));

            high_part.setLayout(new BorderLayout());
            high_part.add(meteo_panel, BorderLayout.WEST);
            high_part.add(capital_panel, BorderLayout.EAST);





            //*****ENERGY CHART*****//

            // row keys (also used for the legend of the energy chart)
            String energy_needed = "Besoin en énergie";
            String energy_available = "energie disponible";
            String green_rank = "Green rank";

            // Column keys
            // voir tableau de PlayerNames[i]

            // create the datasets for the needed/ available energy and the green rank
            DefaultCategoryDataset energy_dataset = new DefaultCategoryDataset();
            DefaultCategoryDataset greenRank_dataset = new DefaultCategoryDataset();
            
            // Add the value of the available/ needed energy and green rank to the datasets for each players
            for (int i = 0; i < game.getPlayers().size(); i++) {
                energy_dataset.addValue(game.getPlayers().get(i).getPowerNeed(), energy_needed, playerName[i]);
                energy_dataset.addValue(game.getPlayers().get(i).getPowerAvailable(), energy_available, playerName[i]);
                greenRank_dataset.addValue(game.getPlayers().get(i).getGreenRank(), green_rank, playerName[i]);
            }


            // create the 3D energy chart 
            JFreeChart energy_chart = ChartFactory.createBarChart3D(
                    "Energy", // chart title
                    "Players", // domain axis label
                    "Value", // range axis label
                    energy_dataset, // data
                    PlotOrientation.VERTICAL,
                    true, // include legend
                    true,
                    false);

            // set the background color for the chart...
            //energy_chart.setBackgroundPaint(new Color(0xCC, 0xFF, 0xCC));
            // Set the legend position
            //chart.getLegend().setAnchor(Legend.SOUTH);

            // get a reference to the plot for further customisation
            CategoryPlot plot = energy_chart.getCategoryPlot();
            plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
            plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
          
            CategoryItemRenderer renderer1 = plot.getRenderer();
            renderer1.setSeriesPaint(0, Color.red); // Set the color of the different bar in the energy chart
            renderer1.setSeriesPaint(1, Color.green);
            renderer1.setSeriesPaint(2, Color.blue);


            // Creation of the Green rank axis on the energy chart
            ValueAxis axis2 = new NumberAxis3D("Green rank value");
            axis2.setRange(0, 10);
            plot.setRangeAxis(1, axis2);
            plot.setRangeGridlinesVisible(true);
            plot.setDataset(1, greenRank_dataset);
            plot.mapDatasetToRangeAxis(1, 1);
            
            // Set the curve of the Green rank
            CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
            renderer2.setSeriesPaint(0, Color.blue);
            plot.setRenderer(1, renderer2);
            plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);

            // add the energy chart to a panel
            ChartPanel energy_chartPanel = new ChartPanel(energy_chart);
            energy_chartPanel.setPreferredSize(new java.awt.Dimension(1300, 270)); // set the chartPanel dimension
            low_part.add(energy_chartPanel); // Add the chartPanel to the high_part JPanel

            // Window space organisation
            second_window.add(high_part, BorderLayout.NORTH); //add the high_part JPanel to the north of the window
            second_window.add(low_part, BorderLayout.SOUTH); //add the low_part JPanel to the south of the window
            second_window.pack();
            second_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            second_window.setVisible(true); // Set the visibilityh of the window on





            /**
             * ******************************************************************************************************************************************************
             */
        } catch (ClientException ex) {
            System.out.println(ex);
        }
    }

  public static ArrayList<String> liste_building(Game game,String nomJoueur,int typeIA){
        
        ArrayList<String> liste_building = new ArrayList<String>();
        
        int coutTotal = 0;
        int nrjNecessaire = 0;
        int playersAlive = 0;
        
        for(int i=0;i<game.getPlayers().size();i++){
            
            if(game.getPlayers().get(i).getName().equals(nomJoueur)){
                
                nrjNecessaire = game.getPlayers().get(i).getPowerNeed() - nrjProduction(game,nomJoueur);
                
                switch(typeIA){
                    
                    case 1 : //ARGENT
                        
                        if(game.getTurn() == 1){
                            for(int j=0;j<25;j++){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        if(game.getTurn() == 2){
                            for(int j=0;j<15;j++){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        if(game.getTurn() == 3){
                            for(int j=0;j<10;j++){
                                if(coutTotal+500 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("water_turbine");
                                    coutTotal += 500;
                                }
                            }
                        }
                        if(game.getTurn() > 10){
                            if(game.getPowerPrice()>0.08){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        for(int l=1;l<10;l++){
                            if(game.getTurn() == (l*5)){
                                for(int k=0;k<game.getPlayers().size();k++){
                                    if(game.getPlayers().get(k).getState() == game.getPlayers().get(i).getState()){
                                        playersAlive++;
                                    }
                                }
                                if(playersAlive == 1){
                                    if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                        liste_building.add("coal_fired_plant");
                                        coutTotal += 400;
                                    }
                                }
                            }
                            for(int k=0;k<5;k++){
                                if(game.getTurn() == (l*10)+k){
                                    if(game.getPlayers().get(i).getGreenRank() > 1){
                                        for(int j=0;j<5;j++){
                                            if(coutTotal+100 <= game.getPlayers().get(i).getCash()){
                                                liste_building.add("wind_turbine");
                                                coutTotal += 100;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        break;
                        
                    case 2 : //ECO
                        
                        if(game.getTurn() == 1){
                            for(int j=0;j<20;j++){
                                if(coutTotal+500 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("water_turbine");
                                    coutTotal += 500;
                                }
                            }
                        }
                        if(game.getTurn() == 2){
                            for(int j=0;j<6;j++){
                                if(coutTotal+500 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("water_turbine");
                                    coutTotal += 500;
                                }
                            }
                        }
                        if(game.getTurn() > 10){
                            if(game.getPowerPrice()>0.2){
                                if(coutTotal+500 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("water_turbine");
                                    coutTotal += 500;
                                }
                            }
                        }
                        for(int l=1;l<10;l++){
                            for(int k=0;k<5;k++){
                                if(game.getTurn() == (l*10)+k){
                                    if(game.getPlayers().get(i).getGreenRank() > 1){
                                        for(int j=0;j<5;j++){
                                            if(coutTotal+100 <= game.getPlayers().get(i).getCash()){
                                                liste_building.add("wind_turbine");
                                                coutTotal += 100;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        break;
                        
                    case 3 : //Pas ECO
                        
                        if(game.getTurn() == 1){
                            for(int j=0;j<25;j++){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        if(game.getTurn() == 2){
                            for(int j=0;j<15;j++){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        if(game.getTurn() > 10){
                            if(game.getPowerPrice()>0.08){
                                if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                    liste_building.add("coal_fired_plant");
                                    coutTotal += 400;
                                }
                            }
                        }
                        for(int l=1;l<10;l++){
                            if(game.getTurn() == (l*5)){
                                for(int k=0;k<game.getPlayers().size();k++){
                                    if(game.getPlayers().get(k).getState() == game.getPlayers().get(i).getState()){
                                        playersAlive++;
                                    }
                                }
                                if(playersAlive == 1){
                                    if(coutTotal+400 <= game.getPlayers().get(i).getCash()){
                                        liste_building.add("coal_fired_plant");
                                        coutTotal += 400;
                                    }
                                }
                            }
                        }
                        
                        break ;
                }
                
                
            }
        }

        return liste_building;
    }

    public static int nrjProduction(Game game,String nomJoueur){
        
        int nrjProduction = 0;
        int light,water,wind;
        
        //La meteo est à 0;
        light = game.getLight();
        water = game.getWater();
        wind = game.getWind();
        
        for(int i=0;i<game.getPlayers().size();i++){
            
            if(game.getPlayers().get(i).getName().equals(nomJoueur)){
                
                for(int j=0;j<game.getPlayers().get(i).getBuildings().size();j++){
                    
                    if(game.getPlayers().get(i).getBuildings().get(j).getExpireAt() != game.getTurn()){
                    
                        switch (game.getPlayers().get(i).getBuildings().get(j).getType()) {
                            case "nuclear":
                                nrjProduction += 1000;
                                break;
                            case "wind_turbine":
                                nrjProduction += wind;
                                break;
                            case "solar_plant":
                                nrjProduction += light;
                                break;
                            case "water_turbine":
                                nrjProduction += 150 + water*150/100;
                                break;
                            case "coal_fired_plant":
                                nrjProduction += 500;
                                break;
                        }
                }
                }
            }
        }
        
        System.out.println("NRJ Production : "+nrjProduction);
        
        return nrjProduction;
    }

    public static int nrjFuture(Game game,ArrayList<String> liste_building){
        
        int nrjFuture = 0;
        int light,water,wind;
        
        //Lors d'une construction, on met les météos à 0
        light = game.getLight();
        water = game.getWater();
        wind = game.getWind();
                
        for(int j=0;j<liste_building.size();j++){
            
            switch (liste_building.get(j)) {
                case "nuclear":
                    nrjFuture += 1000;
                    break;
                case "wind_turbine":
                    nrjFuture += wind;
                    break;
                case "solar_plant":
                    nrjFuture += light;
                    break;
                case "water_turbine":
                    nrjFuture += 150 + water*150/100;
                    break;
                case "coal_fired_plant":
                    nrjFuture += 500;
                    break;
            }
        }
        
        System.out.println("NRJ Future : "+nrjFuture);
        
        return nrjFuture;
    }
    
    
    public static int nrjEco(Game game, String nomJoueur) {

        int nrjEco = 0;

        for (int i = 0; i < game.getPlayers().size(); i++) {

            if (game.getPlayers().get(i).getName().equals(nomJoueur)) {

                for (int j = 0; j < game.getPlayers().get(i).getBuildings().size(); j++) {

                    if (game.getPlayers().get(i).getBuildings().get(j).getType().equals("nuclear")) {

                        nrjEco -= 100;
                    } else if (game.getPlayers().get(i).getBuildings().get(j).getType().equals("wind_turbine")) {

                        nrjEco += 100;
                    } else if (game.getPlayers().get(i).getBuildings().get(j).getType().equals("solar_plant")) {

                        nrjEco += 0;
                    } else if (game.getPlayers().get(i).getBuildings().get(j).getType().equals("water_turbine")) {

                        nrjEco += 300;
                    } else if (game.getPlayers().get(i).getBuildings().get(j).getType().equals("coal_fired_plant")) {

                        nrjEco -= 200;
                    }
                }
            }
        }

        System.out.println("NRJ Eco : " + nrjEco);

        return nrjEco;
    }

  public static int nrjAacheter(Game game,String nomJoueur,ArrayList<String> liste_building, Memoire mem){
        
        double nrjAacheter = 0;
        int besoinNRJ = 0;
        int nrjDispo = 0;
        int nrjProduction;
        int nrjFuture;
        float ratio;
        float cash = 0;
        float prixNRJ;
        float stockNRJ = 0;
        double nrjDispoAvant;
        double ratioReel;
        
        for(int i=0;i<game.getPlayers().size();i++){
            
            if(game.getPlayers().get(i).getName().equals(nomJoueur)){
                
                besoinNRJ = game.getPlayers().get(i).getPowerNeed();
                
                nrjDispo = mem.getNrjDispo();
                
                stockNRJ = game.getPlayers().get(i).getPowerAvailable();
                
                mem.setBesoinNRJ(besoinNRJ);
                
                cash = game.getPlayers().get(i).getCash();
            }
        }
        
        prixNRJ = game.getPowerPrice();
        nrjProduction = nrjProduction(game,nomJoueur);
        nrjFuture = nrjFuture(game,liste_building);
        ratio = stockNRJ/besoinNRJ;
        
        System.out.println("NRJ Dispo :"+nrjDispo);
        System.out.println("Besoin NRJ :"+besoinNRJ);
        System.out.println("Stock NRK :"+stockNRJ);
        System.out.println("ratio :"+ratio);
        
        
        nrjAacheter = besoinNRJ - nrjFuture - nrjProduction - nrjDispo;
        
        if(nrjAacheter < 0){
            nrjAacheter = (besoinNRJ*82)/100 - nrjFuture - nrjProduction - nrjDispo;
        }
        System.out.println("NRJ à acheter : "+nrjAacheter);
                
        if((nrjAacheter*prixNRJ) > cash){
            nrjAacheter = cash / prixNRJ;
        }
        
        nrjDispoAvant = nrjAacheter + nrjFuture + nrjProduction;
        System.out.println("NRJ Dispo Avant :"+nrjDispoAvant);
        
        ratioReel = nrjDispoAvant/besoinNRJ;
        System.out.println("ratio Reel :"+ratioReel);
        
        /*if((ratio <= 0.8 && ratio > 0) || (ratioReel <= 0.08 && ratioReel > 0)){
            nrjAacheter+=100*game.getTurn();
            nrjDispoAvant = nrjAacheter + nrjFuture + nrjProduction;
        }*/
        
        nrjDispo = (int)(nrjAacheter + nrjFuture + nrjProduction - besoinNRJ);
        /*if(nrjDispo > 0){
            nrjDispo = 0;
        }*/
        mem.setNrjDispo(nrjDispo);
        
        /*if(nrjAacheter<0 && prixNRJ<2){
            nrjAacheter = 0;
        }*/
        
        System.out.println("NRJ à acheter modif : "+nrjAacheter);
        
        return (int)nrjAacheter;
    }
  
  
   public static void affichageInformations(Game game,String nomJoueur){
        
        int playersAlive = 0;
        //Ce sont des paramètres pour aider à faire des graphes et les algorithmes de stratégie
        System.out.println("\n****************\n");

        System.out.println("Id du game : "+game.getId());
        System.out.println("Nom du game : "+game.getName());
        System.out.println("Etat du game : "+game.getState());
        System.out.println("Tour : "+game.getTurn());
        System.out.println("Lumière : "+game.getLight());
        System.out.println("Vent : "+game.getWind());
        System.out.println("Pluie : "+game.getWater());
        System.out.println("Prix de l'énergie : "+game.getPowerPrice());
        System.out.println("Joueurs max : "+game.getMaxPlayers());

        System.out.println("Nbre d'events : "+game.getEvents().size());
        for(int i=0;i<game.getEvents().size();i++){
            System.out.println("\tEvent numéro "+i+" dans la liste :");
            System.out.println("\t|type de l'event : "+game.getEvents().get(i).getType());
            System.out.println("\t|Message de l'event : "+game.getEvents().get(i).getMessage());
            System.out.println("\t|Valeur de l'event : "+game.getEvents().get(i).getValue());
            System.out.println("\t|Id du player de l'event : "+game.getEvents().get(i).getPlayerId());
        }

        System.out.println("Nbre de Joueurs : "+game.getPlayers().size());
        for(int i=0;i<game.getPlayers().size();i++){
            if(game.getPlayers().get(i).getName().equals(nomJoueur)){
                System.out.println("\tJoueur numéro "+i+" dans la liste :");
                System.out.println("\t|Id du joueur : "+game.getPlayers().get(i).getId());
                System.out.println("\t|Nom du joueur : "+game.getPlayers().get(i).getName());
                System.out.println("\t|Type du joueur : "+game.getPlayers().get(i).getType());
                System.out.println("\t|Etat du joueur : "+game.getPlayers().get(i).getState());
                for(int k=0;k<game.getPlayers().size();k++){
                    if(game.getPlayers().get(k).getState() == game.getPlayers().get(i).getState()){
                        playersAlive++;
                    }
                }
                System.out.println("Nbre de joueurs ALIVE : "+playersAlive);

                // /!\Les informations complémentaires à propos des buildings sont dans le fichier libraries/api/greenplanetclient/buildings.yml
                System.out.println("\t|Nbre de buildings du joueur : "+game.getPlayers().get(i).getBuildings().size());
                for(int j=0;j<game.getPlayers().get(i).getBuildings().size();j++){
                    System.out.println("\t\t|Building numéro "+j+" dans la liste :");
                    System.out.println("\t\t||Type du building : "+game.getPlayers().get(i).getBuildings().get(j).getType());
                    System.out.println("\t\t||Expiration du building : "+game.getPlayers().get(i).getBuildings().get(j).getExpireAt());
                }

                System.out.println("\t|Cash du joueur : "+game.getPlayers().get(i).getCash());
                System.out.println("\t|Pollution du joueur : "+game.getPlayers().get(i).getPollution());
                System.out.println("\t|Besoin en énergie du joueur : "+game.getPlayers().get(i).getPowerNeed());
                System.out.println("\t|Energie disponible du joueur : "+game.getPlayers().get(i).getPowerAvailable());
                System.out.println("\t|GreenRank du joueur : "+game.getPlayers().get(i).getGreenRank());
            }
        }

        System.out.println("\n****************\n");
    }
}
// window.repaint();