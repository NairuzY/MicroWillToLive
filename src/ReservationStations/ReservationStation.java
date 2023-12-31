package ReservationStations;

import Instructions.Instruction;

public abstract class ReservationStation {
    
    public String tag;
    public boolean busy;
    public Instruction instruction;
    public Float result;
    public int remainingExecutionCycles;
    
    public ReservationStation(String tag) {
        this.tag = tag;
        this.busy = false;
        this.instruction = null;
        
    }
    
    abstract public void execute();
    
    abstract public void empty();

    abstract public void print();
    
    @Override
    public String toString() {
        return "ReservationStation{" +
                "tag='" + tag + '\'' +
                ", busy=" + busy +
                ", instruction=" + instruction +
                ", result=" + result +
                ", remainingExecutionCycles=" + remainingExecutionCycles +
                '}';
    }

    //clone
    abstract public ReservationStation clone();
}
