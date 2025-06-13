package ferryproject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FerryProject {
    public static void main(String[] args) {      
        Ferry f = new Ferry();
        
        Toll toll1 = new Toll("t1", true);
        Toll toll2 = new Toll("t2", true);
        Toll toll3 = new Toll("t3", false);
        Toll toll4 = new Toll("t4", false);
        
        Car c1 = new Car("car-1", 1);
        Car c2 = new Car("car-2", 1);
        Car c3 = new Car("car-3", 1);
        Car c4 = new Car("car-4", 1);
        Car c5 = new Car("car-5", 1);
        Car c6 = new Car("car-6", 1);
        Car c7 = new Car("car-7", 1);
        Car c8 = new Car("car-8", 1);
        Car c9 = new Car("car-9", 1);
        Car c10 = new Car("car-10", 1);
        Car c11 = new Car("car-11", 1);
        Car c12 = new Car("car-12", 1);
        
        Car m1 = new Car("minibus-1", 2);
        Car m2 = new Car("minibus-2", 2);
        Car m3 = new Car("minibus-3", 2);
        Car m4 = new Car("minibus-4", 2);
        Car m5 = new Car("minibus-5", 2);
        Car m6 = new Car("minibus-6", 2);
        Car m7 = new Car("minibus-7", 2);
        Car m8 = new Car("minibus-8", 2);
        Car m9 = new Car("minibus-9", 2);
        Car m10 = new Car("minibus-10", 2);
        
        Car t1 = new Car("truck-1", 3);
        Car t2 = new Car("truck-2", 3);
        Car t3 = new Car("truck-3", 3);
        Car t4 = new Car("truck-4", 3);
        Car t5 = new Car("truck-5", 3);
        Car t6 = new Car("truck-6", 3);
        Car t7 = new Car("truck-7", 3);
        Car t8 = new Car("truck-8", 3);
        
        List<Car> vehicleListA = new ArrayList<Car>();
        List<Car> vehicleListB = new ArrayList<Car>();
        List<Car> allTollAPassedCars = new ArrayList<Car>();
        List<Car> allTollBPassedCars = new ArrayList<Car>();
        
        vehicleListA.add(c1);
        vehicleListA.add(c2);
        vehicleListA.add(c3);
        vehicleListA.add(c4);
        vehicleListA.add(c5);
        vehicleListA.add(c6);
        vehicleListA.add(c7);
        vehicleListA.add(c8);
        vehicleListA.add(c9);
        vehicleListA.add(c10);
        vehicleListA.add(c11);
        vehicleListA.add(c12);
        
        vehicleListA.add(m1);
        vehicleListA.add(m2);
        vehicleListA.add(m3);
        vehicleListA.add(m4);
        vehicleListA.add(m5);
        vehicleListA.add(m6);
        vehicleListA.add(m7);
        vehicleListA.add(m8);
        vehicleListA.add(m9);
        vehicleListA.add(m10);
        
        vehicleListA.add(t1);
        vehicleListA.add(t2);
        vehicleListA.add(t3);
        vehicleListA.add(t4);
        vehicleListA.add(t5);
        vehicleListA.add(t6);
        vehicleListA.add(t7);
        vehicleListA.add(t8);
        
        /* ---------------------- A Side ---------------------- */
        
        while(!f.isCompleted){
            
            //all vehicles in A
            System.out.println("----------All Vehicles in A Side----------");
            for(Car c : vehicleListA){
                System.out.println(c.carName + " is in the A side.");
            }
            
            toll1.isActive = true;
            toll2.isActive = true;
            toll3.isActive = false;
            toll4.isActive = false;
            
            //reset ferry
            f.ferryCars.clear();
            f.currentCapacity = 0;
            f.isFull = false;
            
            for(Car c : vehicleListA){
                c.tollCost = c.setTollCost();
            }
            
            //toll pass process
            for(Car c : vehicleListA){
                if(c.choseToll(toll1, toll2) == toll1){
                    toll1.passControl(c);
                    if(c.isPassedToll == true){
                        c.whichToll = toll1;
                    }
                }
                else{
                    toll2.passControl(c);
                    if(c.isPassedToll == true){
                        c.whichToll = toll2;
                    }
                }
            }
            
            //remove car when car passes from the toll
            Iterator<Car> iterator = vehicleListA.iterator();
            while (iterator.hasNext()) {
                Car c = iterator.next();
                if (c.isPassedToll) {
                    iterator.remove();
                }
            }

            System.out.println("----------Toll Passing Process in A Side----------");
            toll1.printTollPassedCars();
            toll2.printTollPassedCars();

            //merge toll1 and toll2
            allTollAPassedCars.addAll(toll1.tollCars);
            allTollAPassedCars.addAll(toll2.tollCars);
            
            //clear tollCars
            toll1.tollCars.clear();
            toll2.tollCars.clear();

            //Passing ferry process
            int attemptsA = 0;
            int maxAttemptsA = 500; //prevents infinitive loop
            
            while (f.currentCapacity <= f.maxCapacity && attemptsA < maxAttemptsA) {
                if (allTollAPassedCars.isEmpty()) {
                    if (vehicleListA.isEmpty()) {
                        System.out.println("No more vehicles on A side.");
                        break;
                    }

                    Car nextCar = f.getRandomCarFromList(vehicleListA);
                    nextCar.tollCost = nextCar.setTollCost();
                    Toll chosenToll = nextCar.choseToll(toll1, toll2);
                    chosenToll.passControl(nextCar);

                    if (nextCar.isPassedToll) {
                        if ((nextCar.capacity + f.currentCapacity) <= f.maxCapacity) {
                            nextCar.whichToll = chosenToll;
                            allTollAPassedCars.add(nextCar);
                            vehicleListA.remove(nextCar);
                            System.out.println("---Extra---");
                            System.out.println(nextCar.carName + " is passed to the " + chosenToll.tollName);
                        }
                        else{
                            nextCar.isPassedToll = false;
                            chosenToll.tollCars.remove(nextCar);
                        }
                    }
                } else {
                    Car c = f.getRandomCarFromList(allTollAPassedCars);
                    if (!c.isPassedFerry && c.capacity <= (f.maxCapacity - f.currentCapacity)) {
                        if (c.whichToll != null) {
                            f.setPassedFerry(c, c.whichToll);
                            allTollAPassedCars.remove(c);
                        }
                    }
                }

                attemptsA++;
            }
            
            System.out.println("----------Ferry Passing Process A to B----------");
            f.printFerryPassedCars();

            f.tourNumber++;
            
            /* --------------------------- B Side ------------------------------*/

            vehicleListB.addAll(f.ferryCars);
            
            //all vehicles in B
            System.out.println("----------All Vehicles in B Side----------");
            for(Car c : vehicleListB){
                System.out.println(c.carName + " is in the B side.");
            }
            
            //reset ferry
            f.ferryCars.clear();
            f.currentCapacity = 0;
            f.isFull = false;
            
            //reset car
            for (Car c : vehicleListB) {
                c.isPassedToll = false;
                c.isPassedFerry = false;
                c.whichToll = null;
                c.inASide = false;
                c.tollCost = c.setTollCost();
            }

            if(f.tourNumber == 1){
                f.tourNumber++;
                continue;
            }

            toll1.isActive = false;
            toll2.isActive = false;
            toll3.isActive = true;
            toll4.isActive = true;

            //toll pass process
            for(Car c : vehicleListB){
                if(c.choseToll(toll3, toll4) == toll3){
                    toll3.passControl(c);
                    if(c.isPassedToll == true){
                        c.whichToll = toll3;
                    }
                }
                else{
                    toll4.passControl(c);
                    if(c.isPassedToll == true){
                        c.whichToll = toll4;
                    }
                }
            }

            //remove car when car passes from the toll
            Iterator<Car> iteratorB = vehicleListB.iterator();
            while (iteratorB.hasNext()) {
                Car c = iteratorB.next();
                if (c.isPassedToll) {
                    iteratorB.remove();
                }
            }
            
            System.out.println("----------Toll Passing Process in B Side----------");
            toll3.printTollPassedCars();
            toll4.printTollPassedCars();
            
            //merge toll3 and toll4
            allTollBPassedCars.addAll(toll3.tollCars);
            allTollBPassedCars.addAll(toll4.tollCars);
            
            //clear tollCars
            toll3.tollCars.clear();
            toll4.tollCars.clear();
            
            //passing ferry process
            int attemptsB = 0;
            int maxAttemptsB = 500; //prevents infinitive loop

            while (f.currentCapacity <= f.maxCapacity && attemptsB < maxAttemptsB) {
                if (allTollBPassedCars.isEmpty()) {
                    if (vehicleListB.isEmpty()) {
                        System.out.println("No more vehicles on B side.");
                        break;
                    }

                    Car nextCar = f.getRandomCarFromList(vehicleListB);
                    nextCar.tollCost = nextCar.setTollCost();
                    Toll chosenToll = nextCar.choseToll(toll3, toll4);
                    chosenToll.passControl(nextCar);

                    if (nextCar.isPassedToll) {
                        if ((nextCar.capacity + f.currentCapacity) <= f.maxCapacity) {
                            nextCar.whichToll = chosenToll;
                            allTollBPassedCars.add(nextCar);
                            vehicleListB.remove(nextCar);
                            System.out.println("---Extra---");
                            System.out.println(nextCar.carName + " is passed to the " + chosenToll.tollName);
                        }
                        else{
                            nextCar.isPassedToll = false;
                            chosenToll.tollCars.remove(nextCar);
                        }
                    }
                } else {
                    Car c = f.getRandomCarFromList(allTollBPassedCars);
                    if (!c.isPassedFerry && c.capacity <= (f.maxCapacity - f.currentCapacity)) {
                        if (c.whichToll != null) {
                            f.setPassedFerry(c, c.whichToll);
                            allTollBPassedCars.remove(c);
                        }
                    }
                }

                attemptsB++;
            }
            
            System.out.println("----------Ferry Passing Process B to A----------");
            f.printFerryPassedCars();
            
            f.tourNumber++;
            
            if(vehicleListA.isEmpty() && allTollAPassedCars.isEmpty() 
                    && vehicleListB.isEmpty()&& allTollBPassedCars.isEmpty()){
                f.isCompleted = true;
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println("Ferry process is completed");
            }
        }
        
        

    }
}
