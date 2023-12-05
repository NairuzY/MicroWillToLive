package Instructions;

import Storage.RegisterFile;

public class AddI extends Instruction {
    public int sourceRegister1;
    public int imm;
    
    public AddI(int destinationRegister, int sourceRegister1, int imm) {
        super(InstructionType.INT_ADDI, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.imm = imm;
    } 
    
       public float execute(float Vj, float Vk) {
        return Vj +Vk;
    }
}
