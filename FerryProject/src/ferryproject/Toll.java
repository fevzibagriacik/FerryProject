package ferryproject;

import java.util.ArrayList;
import java.util.List;

public class Toll {
    String tollName;
    int cost;
    boolean isActive;
    List<Car> tollCars;
    
    public Toll(String tollName, boolean inASide){
        this.tollName = tollName;
        cost = 50;
        isActive = false;
        tollCars = new ArrayList<Car>();
    }
    
    //add car to the toll
    public void passControl(Car c){
        if(cost <= c.tollCost && !c.isPassedToll && isActive){
            c.isPassedToll = true;
            this.tollCars.add(c);
        }
    }
    
    public void printTollPassedCars(){  
        for(Car c : tollCars){
            System.out.println(c.carName + " is passed to the " + this.tollName);
        }
        //System.out.println("----------------------------------");
    }
}
