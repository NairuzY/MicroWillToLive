package ReservationStations;

import Instructions.Instruction;

public class Load extends ReservationStation {
    private Integer effectiveAddress;

    public Load(String tag) {

        super(tag);

        this.effectiveAddress = null;
    }

    public Integer getEffectiveAddress() {
        return effectiveAddress;
    }

    public void setEffectiveAddress(Integer effectiveAddress) {
        this.effectiveAddress = effectiveAddress;
    }

    public void setValues(Integer effectiveAddress, Instruction instruction) {
        this.effectiveAddress = effectiveAddress;
        this.instruction = instruction;
    }

    @Override
    public void execute() {
        this.instruction.execute();
    }
}