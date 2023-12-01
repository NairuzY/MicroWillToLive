package Instructions;

public class FpMul extends Instruction {
    private int sourceRegister1;
    private int sourceRegister2;

    public FpMul(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.FP_MUL, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    @Override
    public void execute() {
        // Implement FP_ADD instruction execution logic
    }
}
