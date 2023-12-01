import ReservationStations.*;

import java.io.*;
import java.util.*;

import Instructions.Instruction;

public class Main {

    int cycle = 0;
    static Instruction toBeIssued;
    static int addLatency;
    static int subLatency;
    static int multLatency;
    static int divLatency;
    static int loadLatency;
    static int storeLatency;
    static int addReservationStations;
    static int multReservationStations;
    static int loadBuffer;
    static int storeBuffer;
    static Add[] addReservationStation;
    static Multiply[] multReservationStation;
    static Load[] loadReservationStation;
    static Store[] storeReservationStation;

    public static void fetch(String instruction) {
        if (toBeIssued == null) {
            // fetch instruction from memory
            // decode instruction
            // create instruction object
        }
    }

    public static void issue() {
        if (toBeIssued != null) {
            // check if there is space in the reservation station
            // if there is space, add the instruction to the reservation station
            // if there is no space, do nothing
        }
    }

    public static void execute() {
        // check if there is an instruction in any of the reservation stations
        // if there is an instruction, execute it
        // if there is no instruction, do nothing
    }

    public static void write() {
        // check if there is an instruction in any of the reservation stations with
        // remaining time = 0
        // if there is an instruction, write it to everything waiting for it
        // if there is no instruction, do nothing
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
        sc.close();
    }

    public static void main(String[] args) throws IOException {

        start();
        File file = new File("program.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String instruction;
        while ((instruction = br.readLine()) != null) {

        }
        br.close();

    }

}