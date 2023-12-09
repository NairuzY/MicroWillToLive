package Tomasulo;

import Instructions.Instruction;
import ReservationStations.Load;
import ReservationStations.Store;
import ReservationStations.*;
import Storage.Memory;
import Storage.Register;
import Storage.RegisterFile;

import java.util.ArrayList;

import static Tomasulo.Simulator.Program;


// This class saves the state at every cycle, so we can have a history to show on the front-end.
public class State {
     public Add[] addReservationStation;
     public Multiply[] multReservationStation;
     public Load[] loadReservationStation;
     public Store[] storeReservationStation;
     public float []memoryValues;
     public ArrayList<Instruction> program;

     // clone for floatRegister and intRegister
     public Register[] floatRegisterFile;
    public Register[] integerRegisterFile;


     public State(Add[] addReservationStation, Multiply[] multReservationStation, Load[] loadReservationStation, Store[] storeReservationStation
    )
      {
          //clone the reservation stations
            this.addReservationStation = new Add[addReservationStation.length];
            for (int i = 0; i < addReservationStation.length; i++) {
                this.addReservationStation[i] = addReservationStation[i].clone();
            }

            this.multReservationStation = new Multiply[multReservationStation.length];
            for (int i = 0; i < multReservationStation.length; i++) {
                this.multReservationStation[i] = multReservationStation[i].clone();
            }

            this.loadReservationStation = new Load[loadReservationStation.length];
            for (int i = 0; i < loadReservationStation.length; i++) {
                this.loadReservationStation[i] = loadReservationStation[i].clone();
            }

            this.storeReservationStation = new Store[storeReservationStation.length];
            for (int i = 0; i < storeReservationStation.length; i++) {
                this.storeReservationStation[i] = storeReservationStation[i].clone();
            }

            this.memoryValues = new float[Memory.values.length];
            for (int i = 0; i < Memory.values.length; i++) {
                this.memoryValues[i] = Memory.values[i];
            }

            //clone the register files
            this.floatRegisterFile = new Register[RegisterFile.floatRegisterFile.length];
            for (int i = 0; i < RegisterFile.floatRegisterFile.length; i++) {
                this.floatRegisterFile[i] = RegisterFile.floatRegisterFile[i].clone();
            }

            this.integerRegisterFile = new Register[RegisterFile.integerRegisterFile.length];
            for (int i = 0; i < RegisterFile.integerRegisterFile.length; i++) {
                this.integerRegisterFile[i] = RegisterFile.integerRegisterFile[i].clone();
            }

            this.program = new ArrayList<>();
            for (Instruction instruction : Program) {
                this.program.add(instruction.clone());
            }




     }
}
