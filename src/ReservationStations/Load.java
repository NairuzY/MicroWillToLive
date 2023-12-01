package ReservationStations;

import Instructions.FpAdd;
import Instructions.FpSub;
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

    public void setValues( Instruction instruction) {
        this.effectiveAddress = ((Instructions.Load)instruction).effectiveAddress;
        this.instruction = instruction;
        this.busy=true;
        instruction.status=Status.ISSUED;
    }

    @Override
    public void execute() {

        result = ((Instructions.Load)  this.instruction).execute();
    }

    public void empty() {
        this.effectiveAddress = null;
        this.instruction = null;
        this.busy = false;
        this.result = null;
        this.remainingExecutionCycles = -1;
    }
  
}