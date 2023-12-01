package ReservationStations;

import Instructions.Instruction;

public class Store extends ReservationStation {

    int Vj;
    int Qj;
    public Integer address;

    public Store(String tag) {
        super(tag);
        this.address = null;
    }

    public void setValues(int Vj, int Qj, Integer address, Instruction instruction) {
        this.Vj = Vj;
        this.Qj = Qj;
        this.address = address;
        this.instruction = instruction;
    }

    @Override
    public void execute() {
        this.instruction.execute();
    }
}