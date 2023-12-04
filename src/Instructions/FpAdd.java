package Instructions;

public class FpAdd extends Instruction {
    public int sourceRegister1;
    public int sourceRegister2;
    
    public FpAdd(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.FP_ADD, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }
    
    
    public float execute(float Vj, float Vk) {
        return Vj + Vk;
    }
}
