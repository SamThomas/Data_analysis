/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 *
 * @author Tamatoa
 */
public class Regles extends JFrame{
    private JFrame pan;
    private JLabel image ;
    
    public Regles(){
        pan = new JFrame();
        image = new JLabel(new ImageIcon("regles.png"));
        
        pan.setTitle("Projet Informatique");
        pan.setSize(1200, 600);
        pan.setLocationRelativeTo(null);
        pan.add(image);
        //Ajout du bouton à notre content pane
        

        pan.setVisible(true);
        
    }
    
    
}
