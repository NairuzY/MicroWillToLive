package ReservationStations;

import Instructions.Instruction;
import Storage.RegisterFile;
import utils.Status;

public class Store extends ReservationStation {

    float Vj;
    public String Qj;
    public Integer address;

    public Store(String tag) {
        super(tag);
        this.address = null;
    }

    public void setValues(Instruction instruction) {
        instruction.status=Status.ISSUED;
        int source;

        source = ((Instructions.Store) instruction).destinationRegister;

        if (RegisterFile.registerFile[source].tag == null)
            this.Vj = RegisterFile.registerFile[source].value;
        else{
            this.Qj = RegisterFile.registerFile[source].tag;
            instruction.status=Status.WAITING_REGISTER;
        }
        this.instruction = instruction;
        this.address=((Instructions.Store) instruction).effectiveAddress;
        this.busy=true;
      
    }

    @Override
    public void execute() {
        ((Instructions.Store)  this.instruction).execute();
    }
}