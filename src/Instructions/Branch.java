package Instructions;

public class Branch extends Instruction {
    public int targetAddress;
    public int sourceRegister;

    public Branch(String rawInstructionString,int sourceRegister) {
        super( rawInstructionString,InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = 0;
    }

    public float execute(float Vj, float Vk) {
        if (Vj == Vk)
            return 1; //the false condition so that when pc is multiplied by it, it will remain the same and we won't branch
        return 0;
        // Simulator.pc = 0;
    }

    public Instruction clone() {
        Branch clone = new Branch( rawInstructionString,this.sourceRegister);
        clone.executedCycle = this.executedCycle;
        clone.finishedECycle = this.finishedECycle;
        clone.issuedCycle = this.issuedCycle;
        clone.writtenCycle = this.writtenCycle;
        return clone;
    }
}