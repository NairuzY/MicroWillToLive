package Instructions;


public class SubI extends Instruction {
    public int sourceRegister1;
    public int imm;

    public SubI(String rawInstructionString,int destinationRegister, int sourceRegister1, int imm) {
        super(rawInstructionString,InstructionType.INT_SUBI, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.imm = imm;
    }


    public float execute(float Vj, float Vk) {
        return Vj - Vk;
    }

    public Instruction clone() {
        SubI clone = new SubI(this.rawInstructionString,this.destinationRegister, this.sourceRegister1, this.imm);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}
