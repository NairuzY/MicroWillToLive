package Tomasulo;

import ReservationStations.*;
import Storage.Memory;
import Storage.RegisterFile;
import utils.Status;

import java.io.*;
import java.util.*;

import Instructions.AddI;
import Instructions.Branch;
import Instructions.FpAdd;
import Instructions.FpDiv;
import Instructions.FpMul;
import Instructions.FpSub;
import Instructions.Instruction;
import Instructions.InstructionType;
import Instructions.SubI;

public class Simulator {

    static int cycle = 0;
    public static int pc = 0;
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
    static ArrayList<Instruction> Program = new ArrayList<Instruction>();
    static Add[] addReservationStation;
    static Multiply[] multReservationStation;
    static Load[] loadReservationStation;
    static Store[] storeReservationStation;

    static  RegisterFile registerFile = new RegisterFile();
    static Memory memory = new Memory(4);

    public static void ConvertToInstruction() throws IOException {
        InputStream is = Simulator.class.getResourceAsStream("/Tomasulo/program.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String instruction;
        while ((instruction = br.readLine()) != null) {
            String[] fields = instruction.split(" ");
            switch (fields[0]) {
                case "ADD.D":
                    Instruction add = new FpAdd(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(add);
                    break;
                case "SUB.D":
                    Instruction sub = new FpSub(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(sub);
                    break;
                case "MUL.D":
                    Instruction mul = new FpMul(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(mul);
                    break;
                case "DIV.D":
                    Instruction div = new FpDiv(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(div);
                    break;
                case "ADDI":
                    Instruction addI = new AddI(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(addI);
                    break;
                case "SUBI":
                    Instruction subI = new SubI(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(subI);
                    break;
                case "BNEZ":

                    Instruction bnez = new Branch(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2]));
                    Program.add(bnez);
                    break;
                case "S.D":
                    Instruction sd = new Instructions.Store(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2]));
                    Program.add(sd);
                    break;
                case "L.D":
                    Instruction ld = new Instructions.Load(Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2]));
                    Program.add(ld);
                    break;
            }

        }

        br.close();
    }

    static void intializeReservationStations() {
        addReservationStation = new Add[addReservationStations];
        multReservationStation = new Multiply[multReservationStations];
        loadReservationStation = new Load[loadBuffer];
        storeReservationStation = new Store[storeBuffer];
        for (int i = 0; i < addReservationStations; i++)
            addReservationStation[i] = new Add("A" + (i + 1));
        for (int i = 0; i < multReservationStations; i++)
            multReservationStation[i] = new Multiply("M" + (i + 1));
        for (int i = 0; i < loadBuffer; i++)
            loadReservationStation[i] = new Load("L" + (i + 1));
        for (int i = 0; i < storeBuffer; i++)
            storeReservationStation[i] = new Store("S" + (i + 1));
    }

    public static void issue() {
        int index = -1;
        if(pc>=Program.size())
            return;

        if (Program.get(pc).type == InstructionType.FP_ADD || Program.get(pc).type == InstructionType.FP_SUB) {
            index = checkEmptyReservationStation(addReservationStation);
            if (index != -1) {
                System.out.println("Pc= " + Program.get(pc));
                System.out.println(RegisterFile.registerFile[Program.get(pc).destinationRegister].tag);
                RegisterFile.registerFile[Program.get(pc).destinationRegister].tag=addReservationStation[index].tag;
                addReservationStation[index].setValues(Program.get(pc++));
                addReservationStation[index].instruction.issuedCycle = cycle;
                System.out.println("Issuing this instruction: " + addReservationStation[index].instruction);
            }

        } else if (Program.get(pc).type == InstructionType.FP_MUL || Program.get(pc).type == InstructionType.FP_DIV) {
            
            index = checkEmptyReservationStation(multReservationStation);
            if (index != -1) {
                multReservationStation[index].setValues(Program.get(pc));
                RegisterFile.registerFile[Program.get(pc++).destinationRegister].tag=multReservationStation[index].tag;
                multReservationStation[index].instruction.issuedCycle = cycle;
                System.out.println("Issuing this instruction: " + multReservationStation[index].instruction);
            }
        }

        else if (Program.get(pc).type == InstructionType.LOAD)

        {
            index = checkEmptyReservationStation(loadReservationStation);
            if (index != -1) {

                loadReservationStation[index].setValues(Program.get(pc));
                RegisterFile.registerFile[Program.get(pc++).destinationRegister].tag=loadReservationStation[index].tag;
                loadReservationStation[index].instruction.issuedCycle = cycle;
                System.out.println("Issuing this instruction: " + loadReservationStation[index].instruction);
            }
        } else if (Program.get(pc).type == InstructionType.STORE) {
            index = checkEmptyReservationStation(storeReservationStation);
            if (index != -1) {
                storeReservationStation[index].setValues(Program.get(pc++));
                storeReservationStation[index].instruction.issuedCycle = cycle;
                System.out.println("Issuing this instruction: " + storeReservationStation[index].instruction);
            }
        }
        /////////////////////////////////////////////////////////////////////////////
        /// we dont handled the case of SUBI & ADDI & BNEZ

    }

    private static int checkEmptyReservationStation(ReservationStation[] reservationStation) {
        for (int i = 0; i < reservationStation.length; i++) {
            if (reservationStation[i].busy == false)
                return i;
        }

        return -1;

    }

    public static void execute() {
        // check if there is an instruction in any of the reservation stations
        // if there is an instruction, execute it
        // if there is no instruction, do nothing
        for (int i = 0; i < addReservationStations; i++) {
            if (addReservationStation[i].busy) {// y2ma Q and Q null execute
                                                // y2ma elstate is already executing nedecrment el remaining time y2ma
                                    // its only issued/ waiting for register s3tha hnghir state l executing
                                                // w nset time
                                                // remaining time ==0 then ready to write back

                if (addReservationStation[i].getQj() == null & addReservationStation[i].getQk() == null) {
                    if (addReservationStation[i].instruction.status == Status.EXECUTING) {
                        addReservationStation[i].remainingExecutionCycles--;
                        if (addReservationStation[i].remainingExecutionCycles == 0) {
                            addReservationStation[i].execute();
                            addReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                        }
                    }


                    if (addReservationStation[i].instruction.status == Status.ISSUED || addReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                        if (addReservationStation[i].instruction.issuedCycle != cycle) {
                            addReservationStation[i].instruction.executedCycle = cycle;
                            addReservationStation[i].instruction.status = Status.EXECUTING;
                            System.out.println("Executing this instruction: " + addReservationStation[i].instruction);
                            if (addReservationStation[i].instruction instanceof FpAdd)
                                addReservationStation[i].remainingExecutionCycles = addLatency;
                            else
                                addReservationStation[i].remainingExecutionCycles = subLatency;
                        }
                    }


                }
            }
        }

        // mul reservation state
        for (int i = 0; i < multReservationStations; i++) {
            if (multReservationStation[i].busy) {
                if (multReservationStation[i].Qj == null & multReservationStation[i].Qk == null) {
                    if (multReservationStation[i].instruction.status == Status.EXECUTING) {

                        multReservationStation[i].remainingExecutionCycles--;
                        if (multReservationStation[i].remainingExecutionCycles == 0) {
                            multReservationStation[i].execute();
                            multReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                        }
                    }


                    if (multReservationStation[i].instruction.status == Status.ISSUED || multReservationStation[i].instruction.status == Status.WAITING_REGISTER){
                        if (multReservationStation[i].instruction.issuedCycle != cycle) {
                            multReservationStation[i].instruction.executedCycle = cycle;
                            multReservationStation[i].instruction.status = Status.EXECUTING;
                            System.out.println("Executing this instruction: " + multReservationStation[i].instruction);
                            if (multReservationStation[i].instruction instanceof FpMul)
                                multReservationStation[i].remainingExecutionCycles = multLatency;
                            else
                                multReservationStation[i].remainingExecutionCycles = divLatency;
                        }
                    }

                }
            }
        }
        // load reservation station
        for (int i = 0; i < loadBuffer; i++) {
            if (loadReservationStation[i].busy) {

                if (loadReservationStation[i].instruction.status == Status.EXECUTING) {

                    loadReservationStation[i].remainingExecutionCycles--;
                    if (loadReservationStation[i].remainingExecutionCycles == 0) {
                        loadReservationStation[i].execute();
                        loadReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                    }
                }


                if (loadReservationStation[i].instruction.status == Status.ISSUED || loadReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                    if (loadReservationStation[i].instruction.issuedCycle != cycle) {
                        loadReservationStation[i].instruction.executedCycle = cycle;
                        loadReservationStation[i].instruction.status = Status.EXECUTING;
                        System.out.println("Executing this instruction: " + loadReservationStation[i].instruction);
                        loadReservationStation[i].remainingExecutionCycles = loadLatency;
                    }
                }

            }
        }
        /// store reservation station
        for (int i = 0; i < storeBuffer; i++) {

            if (storeReservationStation[i].busy) {
                if (storeReservationStation[i].Qj == null) {
                    if (storeReservationStation[i].instruction.status == Status.EXECUTING) {

                        storeReservationStation[i].remainingExecutionCycles--;
                        if (storeReservationStation[i].remainingExecutionCycles == 0) {
                            storeReservationStation[i].execute();
                            storeReservationStation[i].instruction.status = Status.FINISHED;
                            storeReservationStation[i].empty();
                            continue;
                        }
                    }
                    System.out.println("store "+ i + storeReservationStation[i].instruction);
                    if(storeReservationStation[i].instruction.status == Status.ISSUED || storeReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                        if (storeReservationStation[i].instruction.issuedCycle != cycle) {
                            storeReservationStation[i].instruction.executedCycle = cycle;
                            storeReservationStation[i].instruction.status = Status.EXECUTING;
                            System.out.println("Executing this instruction: " + storeReservationStation[i].instruction);
                            storeReservationStation[i].remainingExecutionCycles = storeLatency;
                        }
                    }
                }
            }
        }

    }

    private static String findHighestPriorityKey(HashMap<String, Integer> priority) {
        int maxPriority = Integer.MIN_VALUE;
        String highestPriorityKey = null;
    
        // Iterate through the entries of the HashMap to find the key with the highest value
        for (Map.Entry<String, Integer> entry : priority.entrySet()) {
            if (entry.getValue() > maxPriority) {
                maxPriority = entry.getValue();
                highestPriorityKey = entry.getKey();
            }
        }
    
        return highestPriorityKey;
    }

    public static ReservationStation findReservationStation(String tag) {
        if(tag.charAt(0)=='A'){
            return addReservationStation[Integer.parseInt(tag.substring(1))-1];
        }
        else if(tag.charAt(0)=='M'){
            return multReservationStation[Integer.parseInt(tag.substring(1))-1];
        }
        else if(tag.charAt(0)=='L'){
            return loadReservationStation[Integer.parseInt(tag.substring(1))-1];
        }
        else if(tag.charAt(0)=='S'){
            return storeReservationStation[Integer.parseInt(tag.substring(1))-1];
        }
        else{
            return null;
        }
    }

    public static void highestWritingPriority() {
        HashMap<String, Integer> priority = new HashMap<String, Integer>();
        for(int i=0; i<addReservationStations; i++){
            if(addReservationStation[i].busy){

            if(addReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT)
                priority.put(addReservationStation[i].tag, 0);}
        }
        for(int i=0; i<multReservationStations; i++){
            if(multReservationStation[i].busy){
            if(multReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT)
                priority.put(multReservationStation[i].tag, 0);}
        }
        for(int i=0; i<loadBuffer; i++){
            if(loadReservationStation[i].busy){
            if(loadReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT)
                priority.put(loadReservationStation[i].tag, 0);}
        }
        if(priority.isEmpty())
            return;
        for(int i=0; i<RegisterFile.registerFile.length; i++){
            if(RegisterFile.registerFile[i].tag != null){
                if(priority.containsKey(RegisterFile.registerFile[i].tag))
                    priority.put(RegisterFile.registerFile[i].tag, priority.get(RegisterFile.registerFile[i].tag)+1);
            }
        }
        for(int i=0; i<addReservationStations; i++){
            if(addReservationStation[i].busy == true){
                if(addReservationStation[i].getQj() != null){
                    if(priority.containsKey(addReservationStation[i].getQj()))
                        priority.put(addReservationStation[i].getQj(), priority.get(addReservationStation[i].getQj())+1);
                }
                if(addReservationStation[i].getQk() != null){
                    if(priority.containsKey(addReservationStation[i].getQk()))
                        priority.put(addReservationStation[i].getQk(), priority.get(addReservationStation[i].getQk())+1);
                }
            }
        }
        for(int i=0; i<multReservationStations; i++){
            if(multReservationStation[i].busy == true){
                if(multReservationStation[i].Qj != null){
                    if(priority.containsKey(multReservationStation[i].Qj))
                        priority.put(multReservationStation[i].Qj, priority.get(multReservationStation[i].Qj)+1);
                }
                if(multReservationStation[i].Qk != null){
                    if(priority.containsKey(multReservationStation[i].Qk))
                        priority.put(multReservationStation[i].Qk, priority.get(multReservationStation[i].Qk)+1);
                }
            }
        }
        for(int i=0; i<storeBuffer; i++){
            if(storeReservationStation[i].busy == true){
                if(storeReservationStation[i].Qj != null){
                    if(priority.containsKey(storeReservationStation[i].Qj))
                        priority.put(storeReservationStation[i].Qj, priority.get(storeReservationStation[i].Qj)+1);
                }
            }
        }

        String highestPriorityStation = findHighestPriorityKey(priority);
        ReservationStation station = findReservationStation(highestPriorityStation);
        System.out.println("Writing this station: " + station);
        write(station);

    }

    public static void write(ReservationStation station){
        for(int i=0; i < RegisterFile.registerFile.length; i++){
            if(RegisterFile.registerFile[i].tag == station.tag){
                RegisterFile.registerFile[i].tag = null;
                RegisterFile.registerFile[i].value = station.result;
            }
        }
        for(int i=0; i < addReservationStations; i++){
            if(addReservationStation[i].getQj() == station.tag){
                addReservationStation[i].setQj(null);
                addReservationStation[i].setVj(station.result);
            }
            if(addReservationStation[i].getQk() == station.tag){
                addReservationStation[i].setQk(null);
                addReservationStation[i].setVk(station.result);
            }
        }
        for(int i=0; i < multReservationStations; i++){
            if(multReservationStation[i].Qj == station.tag){
                multReservationStation[i].Qj=null;
                multReservationStation[i].Vj=station.result;
            }
            if(multReservationStation[i].Qk == station.tag){
                multReservationStation[i].Qk=null;
                multReservationStation[i].Vk=station.result;
            }
        }
        for(int i=0; i < storeBuffer; i++){
            if(storeReservationStation[i].Qj == station.tag){
                storeReservationStation[i].Qj=null;
                storeReservationStation[i].Vj=station.result;
            }
        }
        station.instruction.status=Status.FINISHED;
        station.empty();
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
        intializeReservationStations();
        // initialize reservation states
        sc.close();
    }

    public static void main(String[] args) throws IOException {

        start();

        ConvertToInstruction();

        System.out.println("______________________");
        System.out.println("Initial Register file: ");
        RegisterFile.print();
        System.out.println("______________________");

        while (true) {
            System.out.println("Cycle: " + cycle);
            issue();
            execute();
            highestWritingPriority();
            System.out.println("Register file: ");
            RegisterFile.print();
            cycle++;
            System.out.println("______________________");
            boolean isDone = true;
            for(int i=0; i<Program.size(); i++){
                if(Program.get(i).status != Status.FINISHED){
                    isDone = false;
                }
            }
            if(isDone){
                break;
            }
        }
        System.out.println("Final Register file: ");
        RegisterFile.print();
        System.out.println("Final Memory: ");
        memory.print();
    }

}
