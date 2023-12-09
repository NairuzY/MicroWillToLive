package Instructions;


public class DADD extends Instruction {
    public int sourceRegister1;
    public int sourceRegister2;

    public DADD(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.INT_ADD, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }


    public float execute(float Vj, float Vk) {
        return Vj + Vk;
    }


    public Instruction clone() {
        DADD clone = new DADD(this.destinationRegister, this.sourceRegister1, this.sourceRegister2);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        return clone;
    }
}
