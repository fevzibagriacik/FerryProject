    package ferryproject;

    import java.util.List;

    public class Car{
        String carName;
        boolean isPassedToll;
        boolean isPassedFerry;
        int capacity;
        int tollCost;
        boolean inASide;
        Toll whichToll;

        public Car(String carName, int capacity){
            this.carName = carName;
            isPassedToll = false;
            isPassedFerry = false;
            this.capacity = capacity;
            tollCost = setTollCost();
            inASide = true;
            this.whichToll = null;
        }

        public int setTollCost(){
            return (int) (Math.random() * 101);
        }

        public Toll choseToll(Toll t1, Toll t2) {
            return Math.random() < 0.5 ? t1 : t2;
        }
    }
