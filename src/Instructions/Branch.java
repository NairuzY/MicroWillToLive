package Instructions;

import Storage.RegisterFile;
import Tomasulo.Simulator;


public class Branch extends Instruction {
    private int targetAddress;
    private int sourceRegister;
    
    public Branch(int sourceRegister, int targetAddress) {
        super(InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = 0;
    }
    
    
    public void execute(float Vj, float Vk) {
        if (Vj!= Vk)
            Simulator.pc = 0;
    }

}