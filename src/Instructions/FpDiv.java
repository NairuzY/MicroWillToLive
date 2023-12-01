package Instructions;

public class FpDiv extends Instruction {
    public int sourceRegister1;
    public int sourceRegister2;

    public FpDiv(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.FP_DIV, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.sourceRegister2 = sourceRegister2;
    }

    
    public float execute(float Vj,float Vk) {
     return Vj/Vk;
    }
}
