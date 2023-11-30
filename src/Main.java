import java.io.StreamReader;

public class Main {

    public static void main(String[] args) {

        File file = new File("program.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String instruction;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of cycles for add instructions:");
        int addLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for subtract instructions:");
        int subLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for multiply instructions:");
        int multLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for divide instructions:");
        int divLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for load instructions:");
        int loadLatency = sc.nextInt();
        System.out.println("Enter the number of cycles for store instructions:");
        int storeLatency = sc.nextInt();
        System.out.println("Enter the size of the add/sub reservation station:");
        int AddReservationStations = sc.nextInt();
        System.out.println("Enter the size of the multiply/divide reservation station:");
        int MultReservationStations = sc.nextInt();
        System.out.println("Enter the size of the load buffer:");
        int LoadBuffer = sc.nextInt();
        System.out.println("Enter the size of the store buffer:");
        int StoreBuffer = sc.nextInt();
        while ((instruction = br.readLine()) != null) {
            
        }

    }

}