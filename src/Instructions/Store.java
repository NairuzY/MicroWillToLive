package Instructions;

public class Store extends Instruction {
    private int baseRegister;
    private int offset;

    public Store(int sourceRegister, int baseRegister, int offset) {
        super(InstructionType.STORE, sourceRegister);
        this.baseRegister = baseRegister;
        this.offset = offset;
    }

    @Override
    public void execute() {

    }
}
