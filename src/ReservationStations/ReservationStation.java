package ReservationStations;    

public interface ReservationStation {

    String tag = "";
    boolean busy = false;
    int Address = 0;

    void execute();

}
