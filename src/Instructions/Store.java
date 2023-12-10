package Instructions;

import Storage.Memory;
import Storage.RegisterFile;

public class Store extends Instruction {
    public int effectiveAddress;
    public int sourceRegister1;
    public Store(String rawInstructionString,int sourceRegister, int effectiveAddress) {
        super(rawInstructionString, InstructionType.STORE, -1);
        this.effectiveAddress = effectiveAddress;
        this.sourceRegister1=sourceRegister;
    }


    public void execute(float Vj) {
        Memory.values[effectiveAddress] = Vj;

    }

    public Instruction clone() {
        Store clone = new Store(this.rawInstructionString,this.destinationRegister, this.effectiveAddress);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}
