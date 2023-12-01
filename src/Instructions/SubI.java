package Instructions;

import Storage.RegisterFile;

public class SubI  extends Instruction{
    private int sourceRegister1;
    private int sourceRegister2;

    public SubI(int destinationRegister, int sourceRegister1, int sourceRegister2) {
        super(InstructionType.INT_ADD, destinationRegister);
       
    }

  
    public float execute() {
       return  RegisterFile.registerFile[sourceRegister1].value-RegisterFile.registerFile[sourceRegister2].value;
    }
}
