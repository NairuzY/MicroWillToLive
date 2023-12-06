package ReservationStations;

import Instructions.AddI;
import Instructions.Branch;
import Instructions.DADD;
import Instructions.FpAdd;
import Instructions.FpSub;
import Instructions.Instruction;
import Instructions.InstructionType;
import Instructions.SubI;
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
     
        int source1;
        int source2;
        if (instruction instanceof FpAdd || instruction instanceof FpSub) {
            if (instruction instanceof FpAdd) {
                source1 = ((FpAdd) instruction).sourceRegister1;
                source2 = ((FpAdd) instruction).sourceRegister2;
            } else {
                source1 = ((FpSub) instruction).sourceRegister1;
                source2 = ((FpSub) instruction).sourceRegister2;
            }

            if (RegisterFile.floatRegisterFile[source1].tag == null)
                this.Vj = RegisterFile.floatRegisterFile[source1].value;
            else {
                instruction.status = Status.WAITING_REGISTER;
                this.Qj = RegisterFile.floatRegisterFile[source1].tag;
            }
            if (RegisterFile.floatRegisterFile[source2].tag == null)
                this.Vk = RegisterFile.floatRegisterFile[source2].value;
            else {
                instruction.status = Status.WAITING_REGISTER;
                this.Qk = RegisterFile.floatRegisterFile[source2].tag;
            }
        } else {

            if (instruction instanceof DADD) {

                source1 = ((DADD) instruction).sourceRegister1;
                source2 = ((DADD) instruction).sourceRegister2;
                if (RegisterFile.integerRegisterFile[source2].tag == null)
                    this.Vk = RegisterFile.integerRegisterFile[source2].value;
                else {
                    instruction.status = Status.WAITING_REGISTER;
                    this.Qk = RegisterFile.integerRegisterFile[source2].tag;
                }

            } else if (instruction instanceof AddI) {
                source1 = ((AddI) instruction).sourceRegister1;
                this.Vk = (float) ((AddI) instruction).imm;
            } else if (instruction instanceof SubI) {
                source1 = ((SubI) instruction).sourceRegister1;
                this.Vk = (float) ((SubI) instruction).imm;
            } else {// branch
                source1 = ((Branch) instruction).sourceRegister;
                this.Vk = (float) 0;
            }
            if (RegisterFile.integerRegisterFile[source1].tag == null) {
                this.Vj = RegisterFile.integerRegisterFile[source1].value;
            } else {
                instruction.status = Status.WAITING_REGISTER;
                this.Qj = RegisterFile.integerRegisterFile[source1].tag;
            }

        }
        this.instruction = instruction.clone();
        this.instruction.status = Status.ISSUED;
        this.busy = true;

    }

    public void empty() {
        this.Vj = null;
        this.Vk = null;
        this.Qj = null;
        this.Qk = null;
        this.instruction = null;
        this.busy = false;
        this.result = null;
        this.remainingExecutionCycles = -1;
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
        if (this.instruction instanceof FpAdd)
            result = ((FpAdd) this.instruction).execute(Vj, Vk);
        else
            result = ((FpSub) this.instruction).execute(Vj, Vk);
    }

    @Override
    public void print() {
        System.out.println("{" +
                "tag='" + tag + '\'' +
                ", busy=" + busy +
                ", op=" + (instruction == null ? "0" : (instruction.type == InstructionType.FP_ADD ? "ADD" : "SUB")) +
                ", Vj=" + Vj +
                ", Vk=" + Vk +
                ", Qj='" + Qj + '\'' +
                ", Qk='" + Qk + '\'' +
                '}');
    }

}