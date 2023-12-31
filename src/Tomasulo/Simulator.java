package Tomasulo;

import GUI.Main;
import Instructions.*;
import ReservationStations.Load;
import ReservationStations.Store;
import ReservationStations.*;
import Storage.Memory;
import Storage.RegisterFile;
import utils.Status;

import java.io.*;
import java.util.*;


public class Simulator {

    static int cycle = 1;
    public static int pc = 0;
    static Instruction toBeIssued;
    static int addLatency;
    static int subLatency;
    static int multLatency;
    static int divLatency;
    static int loadLatency;
    static int storeLatency;
    static int integerLatency = 1;
    static int addReservationStations;
    static int multReservationStations;
    static int loadBuffer;
    static int storeBuffer;
    static ArrayList<Instruction> Program = new ArrayList<Instruction>();
    static Add[] addReservationStation;
    static Multiply[] multReservationStation;
    static Load[] loadReservationStation;
    static Store[] storeReservationStation;
    static RegisterFile floatRegisterFile = new RegisterFile();
    static Memory memory = new Memory(10);
    public static ArrayList<Instruction> instructionQueue = new ArrayList<>();
    static boolean issue = true;

    public static ArrayList<State> states = new ArrayList<>();

    public static void ConvertToInstruction() throws IOException {
        BufferedReader br;
        if(Main.instructions == null) {
            InputStream is = Simulator.class.getResourceAsStream("/Tomasulo/program.txt");
             br = new BufferedReader(new InputStreamReader(is));
        }
        else{
            br = new BufferedReader(new StringReader(Main.instructions));
        }
        String instruction;
        while ((instruction = br.readLine()) != null) {
            String[] fields = instruction.split(" ");
            switch (fields[0]) {
                case "ADD.D":
                    Instruction add = new FpAdd(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(add);
                    break;
                case "SUB.D":
                    Instruction sub = new FpSub(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(sub);
                    break;
                case "MUL.D":
                    Instruction mul = new FpMul(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(mul);
                    break;
                case "DIV.D":
                    Instruction div = new FpDiv(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(div);
                    break;
                case "ADDI":
                    Instruction addI = new AddI(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3]));
                    Program.add(addI);
                    break;
                case "SUBI":
                    Instruction subI = new SubI(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3]));
                    Program.add(subI);
                    break;
                case "DADD":
                    Instruction dadd = new DADD(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2].substring(1)), Integer.parseInt(fields[3].substring(1)));
                    Program.add(dadd);
                    break;
                case "BNEZ":
                    Instruction bnez = new Branch(instruction,Integer.parseInt(fields[1].substring(1)));
                    Program.add(bnez);
                    break;
                case "S.D":
                    Instruction sd = new Instructions.Store(instruction,Integer.parseInt(fields[1].substring(1)),
                            Integer.parseInt(fields[2]));
                    Program.add(sd);
                    break;
                case "L.D":
                    Instruction ld = new Instructions.Load(instruction,Integer.parseInt(fields[1].substring(1)),
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
        if (pc >= Program.size() || !issue)
            return;
        InstructionType type = Program.get(pc).type;
        if (type == InstructionType.FP_ADD || type == InstructionType.FP_SUB) {
            index = checkEmptyReservationStation(addReservationStation);
            if (index != -1) {
                addReservationStation[index].setValues(Program.get(pc));
                addReservationStation[index].instruction.issuedCycle = cycle;
                RegisterFile.floatRegisterFile[Program
                        .get(pc++).destinationRegister].tag = addReservationStation[index].tag;

                System.out.println("Issuing instruction: " + addReservationStation[index].instruction);
                instructionQueue.add(addReservationStation[index].instruction);
            }

        } else if (type == InstructionType.INT_ADDI
                || type == InstructionType.INT_SUBI || type == InstructionType.INT_ADD
                || type == InstructionType.BRANCH) {
            index = checkEmptyReservationStation(addReservationStation);
            if (index != -1) {
                addReservationStation[index].setValues(Program.get(pc));
                addReservationStation[index].instruction.issuedCycle = cycle;
                if (type == InstructionType.BRANCH){
                    pc++;
                    issue = false;
                }
                else {
                    RegisterFile.integerRegisterFile[Program
                            .get(pc++).destinationRegister].tag = addReservationStation[index].tag;
                }
                System.out.println("Issuing instruction: " + addReservationStation[index].instruction);
                instructionQueue.add(addReservationStation[index].instruction);
                
            }
            
        } else if (type == InstructionType.FP_MUL || type == InstructionType.FP_DIV) {

            index = checkEmptyReservationStation(multReservationStation);
            if (index != -1) {
                multReservationStation[index].setValues(Program.get(pc));
                multReservationStation[index].instruction.issuedCycle = cycle;
                RegisterFile.floatRegisterFile[Program
                        .get(pc++).destinationRegister].tag = multReservationStation[index].tag;
                System.out.println("Issuing instruction: " + multReservationStation[index].instruction);
                instructionQueue.add(multReservationStation[index].instruction);
            }
        } else if (type == InstructionType.LOAD) {
            index = checkEmptyReservationStation(loadReservationStation);
            if (index != -1) {
                loadReservationStation[index].setValues(Program.get(pc));
                loadReservationStation[index].instruction.issuedCycle = cycle;
                RegisterFile.floatRegisterFile[Program
                        .get(pc++).destinationRegister].tag = loadReservationStation[index].tag;
                System.out.println("Issuing instruction: " + loadReservationStation[index].instruction);
                instructionQueue.add(loadReservationStation[index].instruction);
            }
        } else if (type == InstructionType.STORE) {
            index = checkEmptyReservationStation(storeReservationStation);
            if (index != -1) {
                storeReservationStation[index].setValues(Program.get(pc++));
                storeReservationStation[index].instruction.issuedCycle = cycle;
                System.out.println("Issuing instruction: " + storeReservationStation[index].instruction);
                instructionQueue.add(storeReservationStation[index].instruction);
            }
        }
    }

    private static int checkEmptyReservationStation(ReservationStation[] reservationStation) {
        for (int i = 0; i < reservationStation.length; i++) {
            if (reservationStation[i].busy == false)
                return i;
        }
        return -1;
    }

    private static boolean checkEmpty(ReservationStation[] reservationStation) {
        for (int i = 0; i < reservationStation.length; i++) {
            if (reservationStation[i].busy == true)
                return false;
        }
        return true;
    }

    public static void execute() {
        // check if there is an instruction in any of the reservation stations
        // if there is an instruction, execute it
        // if there is no instruction, do nothing
        for (int i = 0; i < addReservationStations; i++) {
            if (addReservationStation[i].busy) {
                if (addReservationStation[i].getQj() == null & addReservationStation[i].getQk() == null) {
                    if (addReservationStation[i].instruction.status == Status.ISSUED
                            || addReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                        if (addReservationStation[i].instruction.issuedCycle != cycle) {
                            addReservationStation[i].instruction.executedCycle = cycle;
                            addReservationStation[i].instruction.status = Status.EXECUTING;
                            if (addReservationStation[i].instruction instanceof FpAdd)
                                addReservationStation[i].remainingExecutionCycles = addLatency;
                            else if (addReservationStation[i].instruction instanceof FpSub)
                                addReservationStation[i].remainingExecutionCycles = subLatency;
                            else
                                addReservationStation[i].remainingExecutionCycles = integerLatency;
                        }
                    }
                    if (addReservationStation[i].instruction.status == Status.EXECUTING) {
                        addReservationStation[i].remainingExecutionCycles--;
                        if (addReservationStation[i].remainingExecutionCycles == 0) {
                            addReservationStation[i].execute();
                            addReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                            addReservationStation[i].instruction.finishedECycle = cycle;
                        }
                        System.out.println("Executing instruction: " + addReservationStation[i].instruction + " Remaning executing cycles: " + addReservationStation[i].remainingExecutionCycles);
                    }
                }
            }
        }

        // mul reservation state
        for (int i = 0; i < multReservationStations; i++) {
            if (multReservationStation[i].busy) {
                if (multReservationStation[i].Qj == null & multReservationStation[i].Qk == null) {

                    if (multReservationStation[i].instruction.status == Status.ISSUED
                            || multReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                        if (multReservationStation[i].instruction.issuedCycle != cycle) {
                            multReservationStation[i].instruction.executedCycle = cycle;
                            multReservationStation[i].instruction.status = Status.EXECUTING;
                            if (multReservationStation[i].instruction instanceof FpMul)
                                multReservationStation[i].remainingExecutionCycles = multLatency;
                            else
                                multReservationStation[i].remainingExecutionCycles = divLatency;
                        }
                    }
                    if (multReservationStation[i].instruction.status == Status.EXECUTING) {
                        multReservationStation[i].remainingExecutionCycles--;
                        if (multReservationStation[i].remainingExecutionCycles == 0) {
                            multReservationStation[i].execute();
                            multReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                            multReservationStation[i].instruction.finishedECycle = cycle;
                        }
                        System.out.println("Executing instruction: " + multReservationStation[i].instruction+ " Remaning executing cycles: " + multReservationStation[i].remainingExecutionCycles);
                    }

                }
            }
        }
        // load reservation station
        for (int i = 0; i < loadBuffer; i++) {
            if (loadReservationStation[i].busy) {


                if (loadReservationStation[i].instruction.status == Status.ISSUED
                        || loadReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                    if (loadReservationStation[i].instruction.issuedCycle != cycle) {
                        loadReservationStation[i].instruction.executedCycle = cycle;
                        loadReservationStation[i].instruction.status = Status.EXECUTING;
                        loadReservationStation[i].remainingExecutionCycles = loadLatency;
                    }
                }
                if (loadReservationStation[i].instruction.status == Status.EXECUTING) {
                    loadReservationStation[i].remainingExecutionCycles--;
                    if (loadReservationStation[i].remainingExecutionCycles == 0) {
                        loadReservationStation[i].execute();
                        loadReservationStation[i].instruction.status = Status.WAITING_WRITE_RESULT;
                        loadReservationStation[i].instruction.finishedECycle = cycle;
                    }
                    System.out.println("Executing instruction: " + loadReservationStation[i].instruction+" Remaning executing cycles: " + loadReservationStation[i].remainingExecutionCycles);
                }


            }
        }
        /// store reservation station
        for (int i = 0; i < storeBuffer; i++) {

            if (storeReservationStation[i].busy) {
                if (storeReservationStation[i].Qj == null) {
                    if (storeReservationStation[i].instruction.status == Status.ISSUED
                            || storeReservationStation[i].instruction.status == Status.WAITING_REGISTER) {
                        if (storeReservationStation[i].instruction.issuedCycle != cycle) {
                            storeReservationStation[i].instruction.executedCycle = cycle;
                            storeReservationStation[i].instruction.status = Status.EXECUTING;
                            storeReservationStation[i].remainingExecutionCycles = storeLatency;
                        }
                    }
                    if (storeReservationStation[i].instruction.status == Status.EXECUTING) {
                        storeReservationStation[i].remainingExecutionCycles--;
                        if (storeReservationStation[i].remainingExecutionCycles == 0) {
                            storeReservationStation[i].execute();
                            storeReservationStation[i].instruction.status = Status.FINISHED;
                            storeReservationStation[i].instruction.finishedECycle = cycle;
                            System.out.println("Executing instruction: " + storeReservationStation[i].instruction+" Remaning executing cycles: " + storeReservationStation[i].remainingExecutionCycles);
                            storeReservationStation[i].empty();
                            continue;
                        }
                        System.out.println("Executing instruction: " + storeReservationStation[i].instruction+" Remaning executing cycles: " + storeReservationStation[i].remainingExecutionCycles);
                    }
                }
            }
        }

    }

    private static String findHighestPriorityKey(HashMap<String, Integer> priority) {
        int maxPriority = Integer.MIN_VALUE;
        String highestPriorityKey = null;
        // Iterate through the entries of the HashMap to find the key with the highest
        // value
        for (Map.Entry<String, Integer> entry : priority.entrySet()) {
            if (entry.getValue() > maxPriority) {
                maxPriority = entry.getValue();
                highestPriorityKey = entry.getKey();
            }
        }

        return highestPriorityKey;
    }

    public static ReservationStation findReservationStation(String tag) {
        if (tag.charAt(0) == 'A') {
            return addReservationStation[Integer.parseInt(tag.substring(1)) - 1];
        } else if (tag.charAt(0) == 'M') {
            return multReservationStation[Integer.parseInt(tag.substring(1)) - 1];
        } else if (tag.charAt(0) == 'L') {
            return loadReservationStation[Integer.parseInt(tag.substring(1)) - 1];
        } else if (tag.charAt(0) == 'S') {
            return storeReservationStation[Integer.parseInt(tag.substring(1)) - 1];
        } else {
            return null;
        }
    }

    public static void highestWritingPriority() {
        HashMap<String, Integer> priority = new HashMap<String, Integer>();
        for (int i = 0; i < addReservationStations; i++) {
            if (addReservationStation[i].busy) {
                if (addReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT && addReservationStation[i].instruction.finishedECycle!=cycle) {
                    if (addReservationStation[i].instruction.type == InstructionType.BRANCH) {
                        priority.put(addReservationStation[i].tag, Integer.MAX_VALUE);
                    } else
                        priority.put(addReservationStation[i].tag, 0);
                }
            }
        }
        for (int i = 0; i < multReservationStations; i++) {
            if (multReservationStation[i].busy) {
                if (multReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT && multReservationStation[i].instruction.finishedECycle!=cycle)
                    priority.put(multReservationStation[i].tag, 0);
            }
        }
        for (int i = 0; i < loadBuffer; i++) {
            if (loadReservationStation[i].busy) {
                if (loadReservationStation[i].instruction.status == Status.WAITING_WRITE_RESULT && loadReservationStation[i].instruction.finishedECycle!=cycle)
                    priority.put(loadReservationStation[i].tag, 0);
            }
        }
        if (priority.isEmpty())
            return;
        for (int i = 0; i < RegisterFile.floatRegisterFile.length; i++) {
            if (RegisterFile.floatRegisterFile[i].tag != null) {
                if (priority.containsKey(RegisterFile.floatRegisterFile[i].tag))
                    priority.put(RegisterFile.floatRegisterFile[i].tag,
                            priority.get(RegisterFile.floatRegisterFile[i].tag) + 1);
            }
        }
        for (int i = 0; i < RegisterFile.integerRegisterFile.length; i++) {
            if (RegisterFile.integerRegisterFile[i].tag != null) {
                if (priority.containsKey(RegisterFile.integerRegisterFile[i].tag))
                    priority.put(RegisterFile.integerRegisterFile[i].tag,
                            priority.get(RegisterFile.integerRegisterFile[i].tag) + 1);
            }
        }
        for (int i = 0; i < addReservationStations; i++) {
            if (addReservationStation[i].busy == true) {
                if (addReservationStation[i].getQj() != null) {
                    if (priority.containsKey(addReservationStation[i].getQj()))
                        priority.put(addReservationStation[i].getQj(),
                                priority.get(addReservationStation[i].getQj()) + 1);
                }
                if (addReservationStation[i].getQk() != null) {
                    if (priority.containsKey(addReservationStation[i].getQk()))
                        priority.put(addReservationStation[i].getQk(),
                                priority.get(addReservationStation[i].getQk()) + 1);
                }
            }
        }
        for (int i = 0; i < multReservationStations; i++) {
            if (multReservationStation[i].busy == true) {
                if (multReservationStation[i].Qj != null) {
                    if (priority.containsKey(multReservationStation[i].Qj))
                        priority.put(multReservationStation[i].Qj, priority.get(multReservationStation[i].Qj) + 1);
                }
                if (multReservationStation[i].Qk != null) {
                    if (priority.containsKey(multReservationStation[i].Qk))
                        priority.put(multReservationStation[i].Qk, priority.get(multReservationStation[i].Qk) + 1);
                }
            }
        }
        for (int i = 0; i < storeBuffer; i++) {
            if (storeReservationStation[i].busy == true) {
                if (storeReservationStation[i].Qj != null) {
                    if (priority.containsKey(storeReservationStation[i].Qj))
                        priority.put(storeReservationStation[i].Qj, priority.get(storeReservationStation[i].Qj) + 1);
                }
            }
        }

        String highestPriorityStation = findHighestPriorityKey(priority);
        ReservationStation station = findReservationStation(highestPriorityStation);
        station.instruction.status = Status.WRITING_BACK;
        write(station);
        

    }

    public static void write(ReservationStation station) {
        station.instruction.writtenCycle = cycle;
        System.out.println("Writing instruction: " + station.instruction);
        if (station.instruction.type == InstructionType.BRANCH) {
            pc *= (int) station.result.floatValue();
            issue = true;
        } else {
            for (int i = 0; i < RegisterFile.floatRegisterFile.length; i++) {
                if (Objects.equals(RegisterFile.floatRegisterFile[i].tag, station.tag)) {
                    RegisterFile.floatRegisterFile[i].tag = null;
                    RegisterFile.floatRegisterFile[i].value = station.result;
                }
            }
            for (int i = 0; i < RegisterFile.integerRegisterFile.length; i++) {
                if (Objects.equals(RegisterFile.integerRegisterFile[i].tag, station.tag)) {
                    RegisterFile.integerRegisterFile[i].tag = null;
                    RegisterFile.integerRegisterFile[i].value = station.result;
                }
            }
            for (int i = 0; i < addReservationStations; i++) {
                if (Objects.equals(addReservationStation[i].getQj(), station.tag)) {
                    addReservationStation[i].setQj(null);
                    addReservationStation[i].setVj(station.result);
                }
                if (Objects.equals(addReservationStation[i].getQk(), station.tag)) {
                    addReservationStation[i].setQk(null);
                    addReservationStation[i].setVk(station.result);
                }
            }
            for (int i = 0; i < multReservationStations; i++) {
                if (Objects.equals(multReservationStation[i].Qj, station.tag)) {
                    multReservationStation[i].Qj = null;
                    multReservationStation[i].Vj = station.result;
                }
                if (Objects.equals(multReservationStation[i].Qk, station.tag)) {
                    multReservationStation[i].Qk = null;
                    multReservationStation[i].Vk = station.result;
                }
            }
            for (int i = 0; i < storeBuffer; i++) {
                if (Objects.equals(storeReservationStation[i].Qj, station.tag)) {
                    storeReservationStation[i].Qj = null;
                    storeReservationStation[i].Vj = station.result;
                }
            }
        }
        station.instruction.status = Status.FINISHED;
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

    public static void startFromGUI() throws IOException {

        addLatency = Integer.parseInt(Main.textFields[0].getText());
        subLatency = Integer.parseInt(Main.textFields[1].getText());
        multLatency = Integer.parseInt(Main.textFields[2].getText());
        divLatency = Integer.parseInt(Main.textFields[3].getText());
        loadLatency = Integer.parseInt(Main.textFields[4].getText());
        storeLatency = Integer.parseInt(Main.textFields[5].getText());
        addReservationStations = Integer.parseInt(Main.textFields[6].getText());
        multReservationStations = Integer.parseInt(Main.textFields[7].getText());
        loadBuffer = Integer.parseInt(Main.textFields[8].getText());
        storeBuffer = Integer.parseInt(Main.textFields[9].getText());
        intializeReservationStations();


        run();
    }


    public static void run() throws IOException {
        ConvertToInstruction();

        System.out.println("______________________");
        System.out.println("Initial Register file: ");
        RegisterFile.print();
        System.out.println("______________________");
        states.add(new State(addReservationStation, multReservationStation, loadReservationStation, storeReservationStation));


        while (true) {
            System.out.println('\n' + "Cycle: " + cycle);
            issue();
            execute();
            highestWritingPriority();
            System.out.println("Register file: ");
            RegisterFile.print();
            System.out.println("Load Buffers:");
            for (int i = 0; i < loadBuffer; i++) {
                loadReservationStation[i].print();
            }
            System.out.println("Store Buffers:");
            for (int i = 0; i < storeBuffer; i++) {
                storeReservationStation[i].print();
            }
            System.out.println("Add Reservation Stations:");
            for (int i = 0; i < addReservationStations; i++) {
                addReservationStation[i].print();
            }
            System.out.println("Multiply Reservation Stations:");
            for (int i = 0; i < multReservationStations; i++) {
                multReservationStation[i].print();
            }
            System.out.println("Memory: ");
            Memory.print();
            states.add(new State(addReservationStation, multReservationStation, loadReservationStation, storeReservationStation));
            cycle++;

            boolean isDone = false;
            if (checkEmpty(addReservationStation)
                    && checkEmpty(multReservationStation) &&
                    checkEmpty(loadReservationStation)
                    && checkEmpty(storeReservationStation)) {
                isDone = true;
            }
            if (isDone && pc >= Program.size()) {
                break;
            }

            System.out.println("______________________");
        }

        System.out.println("Queue:");
        for (int i = 0; i < instructionQueue.size(); i++) {
            System.out.println(instructionQueue.get(i));
        }


    }

    public static void main(String[] args) throws IOException {

        start();

        run();
    }

   


}