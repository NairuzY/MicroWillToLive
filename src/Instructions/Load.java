package Instructions;

import Storage.Memory;

public class Load extends Instruction {
    public int effectiveAddress;
    
    public Load(int destinationRegister, int effectiveAddress) {
        super(InstructionType.LOAD, destinationRegister);
        this.effectiveAddress = effectiveAddress;
        
    }
    
    
    public float execute() {
        return Memory.values[effectiveAddress];
    }
    public Instruction clone(){
        return new Load(this.destinationRegister, this.effectiveAddress);
    }
}