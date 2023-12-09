package ReservationStations;

import Instructions.Instruction;
import Storage.RegisterFile;
import utils.Status;

public class Store extends ReservationStation {
    
    public Float Vj;
    public String Qj;
    public Integer address;
    
    public Store(String tag) {
        super(tag);
        this.address = null;
        this.Vj = null;
        this.Qj = null;
    }
    
    public void setValues(Instruction instruction) {
        int source;
        
        source = ((Instructions.Store) instruction).sourceRegister1;
        
        if (RegisterFile.floatRegisterFile[source].tag == null)
            this.Vj = RegisterFile.floatRegisterFile[source].value;
        else {
            this.Qj = RegisterFile.floatRegisterFile[source].tag;
            instruction.status = Status.WAITING_REGISTER;
        }
        this.instruction = instruction.clone();
        this.instruction.status = Status.ISSUED;
        this.address = ((Instructions.Store) instruction).effectiveAddress;
        this.busy = true;
        
    }
    
    @Override
    public void execute() {
        ((Instructions.Store) this.instruction).execute(Vj);
    }
    
    public void empty() {
        this.Vj = null;
        this.Qj = null;
        this.address = null;
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
                ", A=" + address +
                ", Vj=" + Vj +
                ", Qj='" + Qj + '\'' +
                '}');
    }

    //clone
    @Override
    public Store clone() {
        Store clone = new Store(this.tag);
        clone.busy = this.busy;
        if (this.instruction != null)
            clone.instruction = this.instruction.clone();
        clone.result = this.result;
        clone.remainingExecutionCycles = this.remainingExecutionCycles;
        clone.Vj = this.Vj;
        clone.Qj = this.Qj;
        clone.address = this.address;
        return clone;
    }
}