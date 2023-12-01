package Instructions;

public class FpAdd extends Instruction {
    private int sourceRegister1;
    private int sourceRegister2;

    public FpAdd(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.FP_ADD, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    @Override
    public void execute() {

    }
}
