package Instructions;

import Storage.Memory;

public class Load extends Instruction {
    public int effectiveAddress;

    public Load(int destinationRegister, int effectiveAddress) {
        super(InstructionType.LOAD, destinationRegister);
        this.effectiveAddress = effectiveAddress;

    }


    public float execute() {
        return Memory.values[effectiveAddress];
    }

    public Instruction clone() {
        Load clone = new Load(this.destinationRegister, this.effectiveAddress);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        return clone;
    }
}