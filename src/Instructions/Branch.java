package Instructions;

public class Branch extends Instruction {
    public int targetAddress;
    public int sourceRegister;

    public Branch(int sourceRegister) {
        super(InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = 0;
    }

    public float execute(float Vj, float Vk) {
        if (Vj != Vk)
            return 1;
        return 0;
        // Simulator.pc = 0;
    }

    public Instruction clone() {
        return new Branch(this.sourceRegister);
    }
}