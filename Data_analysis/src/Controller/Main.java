package Controller;

import Model.BoucleDeJeu;
import View.Login;


import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// Green Planet
import greenplanetclient.*;
import greenplanetclient.Client;
import greenplanetclient.ClientException;
import greenplanetclient.ClientInterface;
import greenplanetclient.OfflineClient;

/**
 * JFreeChart Libraries
 */
import  org.jfree.chart.ChartFactory;
import  org.jfree.chart.ChartFrame;
import  org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import  org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author adrienkerroux + Sam
 */
public class Main {
    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, JAXBException, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
       
           
    Login obj2 = new Login();
    obj2.displayWindow();  
    } 
}
