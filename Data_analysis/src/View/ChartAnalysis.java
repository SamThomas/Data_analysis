package View;

/**
 * JFreeChart Libraries and Classes
 */
import View.ChartAnalysis;
import greenplanetclient.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.JAXBException;


public class ChartAnalysis {
/*    
   
    public float [] calculPatrimoineBuildings (Game game)
    {
        //Declaration & Initialisation
        float [] patrimoine_buildings;
        float nb_nuclear = 0;
        float nb_wind_turbines = 0;
        float nb_water_turbines = 0;
        float nb_coal_fired_plant = 0;
        float nb_solar_plant = 0;
        
        patrimoine_buildings = new float [game.getPlayers().size()];
        
         for (int i = 0; i < game.getPlayers().size(); i++) {
             
             
             for (int j = 0; j < game.getPlayers().get(i).getBuildings().size(); j++) {
                 
                 switch (game.getPlayers().get(i).getBuildings().get(j).getType()) {
                     
                     case "nuclear":
                         nb_nuclear++;
                         break;                         
                         
                     case "wind_turbine":
                         nb_wind_turbines++;
                         break;
                         
                     case "water_turbines":
                         nb_water_turbines++;
                         break;
                         
                     case "coal_fired_plant":
                         nb_coal_fired_plant++;
                         break;
                         
                     case "solar_plant":
                         nb_solar_plant++;
                         break;       
                 } 
                 
             }
        patrimoine_buildings[i] = nb_nuclear*1000 + nb_wind_turbines*100 + nb_solar_plant*200 + nb_water_turbines*500 + nb_coal_fired_plant*400;       

         }
        
        
         return patrimoine_buildings;
    }
*/
    /**
     * Returns a sample dataset.
     *     
* @return The dataset.
     */
  
  /*
    public PieDataset createDataset(Game game) {
        
        //Declaration & Initialisation
        float [] patrimoine_buildings;
        float nb_nuclear = 0;
        float nb_wind_turbines = 0;
        float nb_water_turbines = 0;
        float nb_coal_fired_plant = 0;
        float nb_solar_plant = 0;

       float [] patrimoine;
       patrimoine = new float [game.getPlayers().size()];
       String [] playerName;
       playerName = new String [game.getPlayers().size()];
       float [] cashJoueur;
       cashJoueur = new float [game.getPlayers().size()];
       
        patrimoine_buildings = new float [game.getPlayers().size()];
        
         for (int i = 0; i < game.getPlayers().size(); i++) {
             
             
             for (int j = 0; j < game.getPlayers().get(i).getBuildings().size(); j++) {
                 
                 switch (game.getPlayers().get(i).getBuildings().get(j).getType()) {
                     
                     case "nuclear":
                         nb_nuclear++;
                         break;                         
                         
                     case "wind_turbine":
                         nb_wind_turbines++;
                         break;
                         
                     case "water_turbines":
                         nb_water_turbines++;
                         break;
                         
                     case "coal_fired_plant":
                         nb_coal_fired_plant++;
                         break;
                         
                     case "solar_plant":
                         nb_solar_plant++;
                         break;       
                 } 
                 
             }
        patrimoine_buildings[i] = nb_nuclear*1000 + nb_wind_turbines*100 + nb_solar_plant*200 + nb_water_turbines*500 + nb_coal_fired_plant*400;       

         }
        
        // create the dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        for (int i = 0; i < game.getPlayers().size(); i++) {
            
            patrimoine[i] = cashJoueur[i] +  patrimoine_buildings[i]; 
            playerName[i] = game.getPlayers().get(i).getName();
            
            dataset.setValue("" +playerName[i] , patrimoine[i]);
                    
        }
        
        return dataset;
    }


    public JFreeChart chartConstruction(Game game) {
        
        
        //Initialisation
        ChartAnalysis handle = new ChartAnalysis();
        Game game;
        this.game =game;
        

        // create the chart...
        /*JFreeChart chart = ChartFactory.createBarChart3D(
                "Quantity of buildings for each players", // chart title
                "Category", // domain axis label
                "Value", // range axis label
                handle.createDataset(), // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips?
                false // URLs?
                );       
        chart.setBackgroundPaint(new Color(0xFFFFFF)); */
        
        
       //Weather chart -> dynamic chart
  
  /*      
        //Capital chart
        JFreeChart chart;
        chart = ChartFactory.createPieChart3D(
     
     "Capital chart", // title 
     handle.createDataset(),
     true, 
     false,
     false
     );
        
        return chart;

    } */
}
