package Instructions;

public class Load extends Instruction {
    private int baseRegister;
    private int offset;

    public Load(int destinationRegister, int baseRegister, int offset) {
        super(InstructionType.LOAD, destinationRegister);
        this.baseRegister = baseRegister;
        this.offset = offset;
    }

    @Override
    public void execute() {

    }
}