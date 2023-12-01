package ReservationStations;

import Instructions.Instruction;

public class Multiply extends ReservationStation {

    int Vj;
    int Vk;
    int Qj;
    int Qk;

    public Multiply(String tag) {
        super(tag);
    }

    public void setValues(int Vj, int Vk, int Qj, int Qk, Instruction instruction) {
        this.Vj = Vj;
        this.Vk = Vk;
        this.Qj = Qj;
        this.Qk = Qk;
        this.instruction = instruction;
    }

    @Override
    public void execute() {
        this.instruction.execute();
    }
}