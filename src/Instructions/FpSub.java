package Instructions;

public class FpSub extends Instruction {
    private int sourceRegister1;
    private int sourceRegister2;

    public FpSub(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.FP_SUB, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    @Override
    public void execute() {
        // Implement FP_ADD instruction execution logic
    }
}
