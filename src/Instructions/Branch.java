package Instructions;

import Tomasulo.Simulator;


public class Branch extends Instruction {
    public int targetAddress;
    public int sourceRegister;
    
    public Branch(int sourceRegister) {
        super(InstructionType.BRANCH, -1);
        this.sourceRegister = sourceRegister;
        this.targetAddress = 0;
    }
    
    
    public void execute(float Vj, float Vk) {
        if (Vj!= Vk)
            Simulator.pc = 0;
    }


    public Instruction clone(){
        return new Branch(this.sourceRegister);
    }
}