package Instructions;

public class Branch extends Instruction {
    private int sourceRegister;
    private int targetAddress;

    public Branch(int sourceRegister, int targetAddress) {
        super(InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = targetAddress;
    }

    @Override
    public void execute() {

    }
}