package ReservationStations;

import Instructions.Instruction;

public abstract class ReservationStation {

    String tag;
    boolean busy;
    Instruction instruction;

    public ReservationStation(String tag) {
        this.tag = tag;
        this.busy = false;
        this.instruction = null;

    }

    public void execute() {
        this.instruction.execute();
    }

}
