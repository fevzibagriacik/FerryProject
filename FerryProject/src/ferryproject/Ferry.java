    package ferryproject;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Random;

    public class Ferry {
        int maxCapacity;
        int currentCapacity;
        boolean isFull;
        List<Car> ferryCars;
        int tourNumber;
        boolean isCompleted;

        public Ferry(){
            maxCapacity = 20;
            currentCapacity = 0;
            isFull = false;
            ferryCars = new ArrayList<Car>();
            tourNumber = 0;
            isCompleted = false;
        }

        public void setIsFull(){
            if(this.currentCapacity == 20){
                this.isFull = true;
            }
            else{
                this.isFull = false;
            }
        }

        //passing process to the ferry
        public void setPassedFerry(Car c, Toll t){
            if(t == null) return; 
            if(!this.isFull 
                    && c.isPassedToll == true 
                    && c.isPassedFerry == false 
                    && (this.maxCapacity - this.currentCapacity) >= c.capacity) {
                c.isPassedFerry = true;
                passFerry(c);
                ferryCars.add(c);
                t.tollCars.remove(c);
            }
        }

        public void passFerry(Car c){
            this.currentCapacity += c.capacity;
        }

        public void printFerryPassedCars(){  
            for(Car c : ferryCars){
                System.out.println(c.carName + " is passed to the ferry");
            }
            //System.out.println("----------------------------------");
        }

        public void printFerryPassedCarsAtBSide(){
            for(Car c : ferryCars){
                System.out.println(c.carName + " reachs to the B side.");
            }
            //System.out.println("----------------------------------");
        }

        public void printFerryPassedCarsAtASide(){
            for(Car c : ferryCars){
                System.out.println(c.carName + " reachs to the A side.");
            }
            //System.out.println("----------------------------------");
        }

        public Car getRandomCar(List<Car> allTollPassedCars, int availableCapacity){
            List<Car> availableCars = new ArrayList<>();
            for (Car c : allTollPassedCars) {
                if (!c.isPassedFerry && c.capacity <= availableCapacity) {
                    availableCars.add(c);
                }
            }

            if (availableCars.isEmpty()) return null;

            Random rnd = new Random();
            return availableCars.get(rnd.nextInt(availableCars.size()));
        }

        public Car getRandomCarFromList(List<Car> carList){
            Random rnd = new Random();
            int rndIndex = rnd.nextInt(carList.size());
            return carList.get(rndIndex);
        }

        public void setAllVehicleASide(List<Car> carList, boolean inASide){
            for(Car c : carList){
                c.inASide = inASide;
            }
        }

        public void setAllVehiclePassedToll(List<Car> carList, boolean isPassedToll){
            for(Car c : carList){
                c.isPassedToll = isPassedToll;
            }
        }

        public void setAllVehiclePassedFerry(List<Car> carList, boolean isPassedFerry){
            for(Car c : carList){
                c.isPassedFerry = isPassedFerry;
            }
        }

    }
