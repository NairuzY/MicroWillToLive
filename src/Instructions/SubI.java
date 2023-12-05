package Instructions;


public class SubI extends Instruction {
    public int sourceRegister1;
    public int imm;
    
    public SubI(int destinationRegister, int sourceRegister1, int imm) {
        super(InstructionType.INT_SUBI, destinationRegister);
        this.sourceRegister1 = sourceRegister1;
        this.imm = imm;
    }
    
    
    public float execute(float Vj, float Vk) {
        return Vj - Vk;
    }
    public Instruction clone(){
        return new SubI(this.destinationRegister, this.sourceRegister1, this.imm);
    }
}
