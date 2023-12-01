import ReservationStations.*;

import java.io.*;
import java.util.*;

public class Main {

    int cycle = 0;
    Instruction toBeIssued; 
    int addLatency;
    int subLatency;
    int multLatency;
    int divLatency;
    int loadLatency;
    int storeLatency;
    int addReservationStations;
    int multReservationStations;
    int loadBuffer;
    int storeBuffer;
    Add[] addReservationStation;
    Multiply[] multReservationStation;
    Load[] loadReservationStation;
    Store[] storeReservationStation;

    public static void fetch(String instruction) {
        if(toBeIssued == null) {

            // fetch instruction from memory
            // decode instruction
            // create instruction object
        }
    }

    public static void start() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of cycles for add instructions:");
        addLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for subtract instructions:");
        subLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for multiply instructions:");
        multLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for divide instructions:");
        divLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for load instructions:");
        loadLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for store instructions:");
        storeLatency = sc.nextInt();
        System.out.println("Enter the size of the add/sub reservation station:");
        addReservationStations = sc.nextInt();
        System.out.println("Enter the size of the multiply/divide reservation station:");
        multReservationStations = sc.nextInt();
        System.out.println("Enter the size of the load buffer:");
        loadBuffer = sc.nextInt();
        System.out.println("Enter the size of the store buffer:");
        storeBuffer = sc.nextInt();

        addReservationStation = new Add[addReservationStations];
        multReservationStation = new Multiply[multReservationStations];
        loadReservationStation = new Load[loadBuffer];
        storeReservationStation = new Store[storeBuffer];

    }
    
    public static void main(String[] args) throws IOException {

        start();
        File file = new File("program.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String instruction;
        while ((instruction = br.readLine()) != null) {
        
        }


    }
    
    

}