/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author adrienkerroux
 */
public class Memoire {
    
    private int besoinNRJ;
    private int debugPowerAvailable;
    private int nrjDispo;
    
    public Memoire(){
        
        nrjDispo = -1;
        besoinNRJ = 100;
        debugPowerAvailable = 1;
    }
    
    public int getNrjDispo(){
        return nrjDispo;
    }
    
    public void setNrjDispo(int val){
        nrjDispo = val;
    }
    
    public int getBesoinNRJ(){
        return besoinNRJ;
    }
    
    public void setBesoinNRJ(int val){
        besoinNRJ = val;
    }
    
    public void maj(){
        if(debugPowerAvailable == 1){
            debugPowerAvailable = -1;
        }else{
            debugPowerAvailable = 1;
        }
    }
    
    public int getDebug(){
        return debugPowerAvailable;
    }
}
