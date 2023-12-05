package Instructions;

import Storage.Memory;
import Storage.RegisterFile;

public class Store extends Instruction {
    public int effectiveAddress;
    
    public Store(int sourceRegister, int effectiveAddress) {
        super(InstructionType.STORE, sourceRegister);
        this.effectiveAddress = effectiveAddress;
    }
    
    public void execute() {
        Memory.values[effectiveAddress] = RegisterFile.floatRegisterFile[destinationRegister].value;
    }
}
