package Instructions;


public class SubI extends Instruction {
    private int sourceRegister1;
    private int imm;
    
    public SubI(int destinationRegister, int sourceRegister1, int imm) {
        super(InstructionType.INT_SUBI, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.imm = imm;
    }
    
    
    public float execute(float Vj, float Vk) {
        return Vj - Vk;
    }
}
