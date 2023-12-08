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
        if (Vj == Vk)
            return 1; //the false condition so that when pc is multiplied by it, it will remain the same and we won't branch
        return 0;
        // Simulator.pc = 0;
    }

    public Instruction clone() {
        return new Branch(this.sourceRegister);
    }
}