package Instructions;

import Storage.Memory;
import Storage.RegisterFile;

public class Store extends Instruction {
    public int effectiveAddress;

    public Store(int sourceRegister, int effectiveAddress) {
        super(InstructionType.STORE, sourceRegister);
        this.effectiveAddress = effectiveAddress;
    }

    public void execute() {
        Memory.values[effectiveAddress] = RegisterFile.floatRegisterFile[destinationRegister].value;
    }

    public Instruction clone() {
        Store clone = new Store(this.destinationRegister, this.effectiveAddress);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        return clone;
    }
}
