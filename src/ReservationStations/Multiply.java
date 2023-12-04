package ReservationStations;

import Instructions.FpDiv;
import Instructions.FpMul;
import Instructions.Instruction;
import Instructions.InstructionType;
import Storage.RegisterFile;
import utils.Status;

public class Multiply extends ReservationStation {
    
    public Float Vj;
    public Float Vk;
    public String Qj;
    public String Qk;
    
    public Multiply(String tag) {
        super(tag);
    }
    
    public void setValues(Instruction instruction) {
        instruction.status = Status.ISSUED;
        int source1;
        int source2;
        if (instruction instanceof FpMul) {
            source1 = ((FpMul) instruction).sourceRegister1;
            source2 = ((FpMul) instruction).sourceRegister2;
        } else {
            source1 = ((FpDiv) instruction).sourceRegister1;
            source2 = ((FpDiv) instruction).sourceRegister2;
        }
        if (RegisterFile.registerFile[source1].tag == null)
            this.Vj = RegisterFile.registerFile[source1].value;
        else {
            this.Qj = RegisterFile.registerFile[source1].tag;
            instruction.status = Status.WAITING_REGISTER;
        }
        if (RegisterFile.registerFile[source2].tag == null)
            this.Vk = RegisterFile.registerFile[source2].value;
        else {
            this.Qk = RegisterFile.registerFile[source2].tag;
            instruction.status = Status.WAITING_REGISTER;
        }
        this.instruction = instruction;
        this.busy = true;
        
    }
    
    @Override
    public void execute() {
        if (this.instruction instanceof FpMul)
            result = ((FpMul) this.instruction).execute(Vj, Vk);
        else
            result = ((FpDiv) this.instruction).execute(Vj, Vk);
    }
    
    public void empty() {
        this.Vj = null;
        this.Vk = null;
        this.Qj = null;
        this.Qk = null;
        this.instruction = null;
        this.busy = false;
        this.result = null;
        this.remainingExecutionCycles = -1;
    }
    
    @Override
    public String toString() {
        return "Multiply{" +
                "tag='" + tag + '\'' +
                ", busy=" + busy +
                ", instruction=" + instruction +
                ", result=" + result +
                ", remainingExecutionCycles=" + remainingExecutionCycles +
                ", Vj=" + Vj +
                ", Vk=" + Vk +
                ", Qj='" + Qj + '\'' +
                ", Qk='" + Qk + '\'' +
                '}';
    }

    @Override
    public void print() {
        System.out.println("{" +
                "tag='" + tag + '\'' +
                ", busy=" + busy +
                ", op=" + (instruction==null?0:(instruction.type == InstructionType.FP_MUL?"MUL":"DIV")) +
                ", Vj=" + Vj +
                ", Vk=" + Vk +
                ", Qj='" + Qj + '\'' +
                ", Qk='" + Qk + '\'' +
                '}');
    }
}