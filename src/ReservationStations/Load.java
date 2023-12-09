package ReservationStations;

import Instructions.Instruction;
import utils.Status;

public class Load extends ReservationStation {
    private Integer effectiveAddress;
    
    public Load(String tag) {
        
        super(tag);
        
        this.effectiveAddress = null;
    }
    
    public Integer getEffectiveAddress() {
        return effectiveAddress;
    }
    
    public void setEffectiveAddress(Integer effectiveAddress) {
        this.effectiveAddress = effectiveAddress;
    }
    
    public void setValues(Instruction instruction) {
        this.effectiveAddress = ((Instructions.Load) instruction).effectiveAddress;
        this.instruction = instruction.clone();
        this.busy = true;
        this.instruction.status = Status.ISSUED;
    }
    
    @Override
    public void execute() {
        
        result = ((Instructions.Load) this.instruction).execute();
    }
    
    public void empty() {
        this.effectiveAddress = null;
        this.instruction = null;
        this.busy = false;
        this.result = null;
        this.remainingExecutionCycles = -1;
    }

    @Override
    public void print() {
        System.out.println("{" +
                "tag='" + tag + '\'' +
                ", busy=" + busy +
                ", A=" + effectiveAddress +
                '}');
    }

    //clone
    @Override
    public Load clone() {
        Load clone = new Load(this.tag);
        clone.busy = this.busy;
        if (this.instruction != null)
            clone.instruction = this.instruction.clone();
        clone.result = this.result;
        clone.remainingExecutionCycles = this.remainingExecutionCycles;
        clone.effectiveAddress = this.effectiveAddress;
        return clone;
    }
}