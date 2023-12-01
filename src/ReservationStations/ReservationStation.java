package ReservationStations;

import Instructions.Instruction;

public abstract class ReservationStation {

  public  String tag;
    public boolean busy;
    public   Instruction instruction;
    float result;
    public int remainingExecutionCycles;

    public ReservationStation(String tag) {
        this.tag = tag;
        this.busy = false;
        this.instruction = null;

    }

    abstract public void execute();

}
