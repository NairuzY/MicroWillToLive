package Instructions;

import Instructions.InstructionType;
import utils.Status;

public abstract class Instruction {
    public InstructionType type;
    public   Status status;
    public int destinationRegister;

    public Instruction(InstructionType type, int destinationRegister) {
        this.type = type;
        this.destinationRegister = destinationRegister;
        status=Status.NOT_ISSUED;
    }

    //  public  float execute(){
        
    //  }
     
    //  public  void execute(float Vj,float Vk);
    //    public  void execute(float Vj);

}
