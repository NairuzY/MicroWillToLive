package Storage;

public class Memory {
 
    int n;
    public static float[] values;
    public Memory(int n){
    values = new float [n];
   }

   public static void print(){
       for(int i=0;i<values.length;i++){
           System.out.println("Memory "+i+" value "+values[i]);
       }
   }
}
