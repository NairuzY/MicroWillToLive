import ReservationStations.*;

import java.io.*;
import java.util.*;

public class Main {

    int cycle = 0;
    
    public static void main(String[] args) throws IOException {

        File file = new File("program.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String instruction;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of cycles for add instructions:");
        int addLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for subtract instructions:");
        int subLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for multiply instructions:");
        int multLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for divide instructions:");
        int divLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for load instructions:");
        int loadLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for store instructions:");
        int storeLatency = sc.nextInt();
        System.out.println("Enter the size of the add/sub reservation station:");
        int addReservationStations = sc.nextInt();
        System.out.println("Enter the size of the multiply/divide reservation station:");
        int multReservationStations = sc.nextInt();
        System.out.println("Enter the size of the load buffer:");
        int loadBuffer = sc.nextInt();
        System.out.println("Enter the size of the store buffer:");
        int storeBuffer = sc.nextInt();
        
        // Reservation stations
        Add[] addReservationStation = new Add[addReservationStations];
        Multiply[] multReservationStation = new Multiply[multReservationStations];
        Load[] loadReservationStation = new Load[loadBuffer];
        Store[] storeReservationStation = new Store[storeBuffer];
        
        while ((instruction = br.readLine()) != null) {
        
        }
    }
    
    

}