package Instructions;

public class FpMul extends Instruction {
    public int sourceRegister1;
    public int sourceRegister2;

    public FpMul(String rawInstructionString,int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(rawInstructionString,InstructionType.FP_MUL, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    public float execute(float Vj, float Vk) {
        return Vj * Vk;
    }

    public Instruction clone() {
        FpMul clone = new FpMul(this.rawInstructionString,this.destinationRegister, this.sourceRegister1, this.sourceRegister2);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}
