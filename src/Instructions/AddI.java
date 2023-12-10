package Instructions;

public class AddI extends Instruction {
    public int sourceRegister1;
    public int imm;

    public AddI(String rawInstructionString,int destinationRegister, int sourceRegister1, int imm) {
        super( rawInstructionString,InstructionType.INT_ADDI, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.imm = imm;
    }

    public float execute(float Vj, float Vk) {
        return Vj + Vk;
    }

    public Instruction clone() {
        AddI clone = new AddI( rawInstructionString,this.destinationRegister, this.sourceRegister1, this.imm);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}
