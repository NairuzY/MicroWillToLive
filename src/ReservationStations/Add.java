package ReservationStations;

import Instructions.FpAdd;
import Instructions.FpSub;
import Instructions.Instruction;
import Storage.RegisterFile;
import utils.Status;

public class Add extends ReservationStation {

    private Float Vj;
    private Float Vk;
    private String Qj;
    private String Qk;

    public Add(String tag) {

        super(tag);

        this.Vj = null;
        this.Vk = null;
        this.Qj = null;
        this.Qk = null;

    }

public void setValues(Instruction instruction) {
    instruction.status=Status.ISSUED;
         int source1;
         int source2;
        if(instruction instanceof FpAdd){
     source1  =((FpAdd)instruction).sourceRegister1;
    source2  =((FpAdd)instruction).sourceRegister2;
        }
else
{
     source1  =((FpSub)instruction).sourceRegister1;
    source2  =((FpSub)instruction).sourceRegister2;
        }
if(RegisterFile.registerFile[source1].tag==null)
  this.Vj = RegisterFile.registerFile[source1].value;
else {instruction.status=Status.WAITING_REGISTER;
 this.Qj = RegisterFile.registerFile[source1].tag;
}      
 if(RegisterFile.registerFile[source2].tag==null)
  this.Vk = RegisterFile.registerFile[source2].value;
else {instruction.status=Status.WAITING_REGISTER;
 this.Qk = RegisterFile.registerFile[source2].tag;}

this.instruction = instruction;
this.busy=true;


}

    public void setVj(Float Vj) {
        this.Vj = Vj;
    }

    public void setVk(Float Vk) {
        this.Vk = Vk;
    }

    public void setQj(String Qj) {
        this.Qj = Qj;
    }

    public void setQk(String Qk) {
        this.Qk = Qk;
    }

    public Float getVj() {
        return Vj;
    }

    public Float getVk() {
        return Vk;
    }

    public String getQj() {
        return Qj;
    }

    public String getQk() {
        return Qk;
    }
    public void execute() {
        if(this.instruction instanceof FpAdd)
   result=   ((FpAdd)  this.instruction).execute(Vj,Vk);
      else 
     result=   ((FpSub)  this.instruction).execute(Vj,Vk);
    }
}