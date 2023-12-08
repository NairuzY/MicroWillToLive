package Instructions;

import utils.Status;

public abstract class Instruction {
    public InstructionType type;
    public Status status;
    public int destinationRegister;
    public int issuedCycle = 0;
    public int executedCycle = 0;
    public int finishedECycle = 0;
    public int writtenCycle = 0;
    
    public Instruction(InstructionType type, int destinationRegister) {
        this.type = type;
        this.destinationRegister = destinationRegister;
        status = Status.NOT_ISSUED;
    }
    
    @Override
    public String toString() {
        return "Instruction{" +
                "type=" + type +
                ", status=" + status +
                ", destination register=" + destinationRegister +
                ", issued cycle=" + issuedCycle +
                ", started execution cycle=" + executedCycle +
                ", finished execution cycle=" + finishedECycle +
                ", written cycle=" + writtenCycle +
                '}';
    }
    
abstract public Instruction clone();  
}
