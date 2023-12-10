package Instructions;

import Storage.Memory;

public class Load extends Instruction {
    public int effectiveAddress;

    public Load(String rawInstructionString, int destinationRegister, int effectiveAddress) {
        super(rawInstructionString, InstructionType.LOAD, destinationRegister);
        this.effectiveAddress = effectiveAddress;

    }


    public float execute() {
        return Memory.values[effectiveAddress];
    }

    public Instruction clone() {
        Load clone = new Load(this.rawInstructionString,this.destinationRegister, this.effectiveAddress);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}