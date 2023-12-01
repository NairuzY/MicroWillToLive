package ReservationStations;

import Instructions.Instruction;

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

    public void setValues(Float Vj, Float Vk, String Qj, String Qk, Instruction instruction) {
        this.Vj = Vj;
        this.Vk = Vk;
        this.Qj = Qj;
        this.Qk = Qk;
        this.instruction = instruction;
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

}