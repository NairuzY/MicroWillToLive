package Instructions;

import Storage.RegisterFile;
import Tomasulo.Simulator;


public class Branch extends Instruction {
    private int targetAddress;
    private int sourceRegister;
    
    public Branch(int sourceRegister, int targetAddress) {
        super(InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = targetAddress;
    }
    
    
    public void execute() {
        if (RegisterFile.registerFile[sourceRegister].value != 0)
            Simulator.pc = targetAddress;
    }
}