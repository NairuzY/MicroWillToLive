package Instructions;

import Instructions.InstructionType;
import utils.Status;

public abstract class Instruction {
    private InstructionType type;
    Status status;
    private int destinationRegister;

    public Instruction(InstructionType type, int destinationRegister) {
        this.type = type;
        this.destinationRegister = destinationRegister;
        status=Status.NOT_ISSUED;
    }

    public abstract void execute();
}
