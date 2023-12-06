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
    }
    
    public void setValues(Instruction instruction) {
        int source;
        
        source = ((Instructions.Store) instruction).destinationRegister;
        
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
        ((Instructions.Store) this.instruction).execute();
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
                '}');
    }
}