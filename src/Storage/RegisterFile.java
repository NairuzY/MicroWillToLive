package Storage;

public class RegisterFile {
    public static Register[] floatRegisterFile;
    public static Register[] integerRegisterFile;
    public RegisterFile() {
        floatRegisterFile = new Register[32];
        integerRegisterFile= new Register[32];
        for (int i = 0; i < 32; i++) {
            floatRegisterFile[i] = new Register(i + 1);
            integerRegisterFile[i]=new Register(i+1);
        }
    }
    
    public static void print() {
        System.out.println("float Registers File");
        for (int i = 0; i < 32; i++) {
            System.out.println("Register " + i + " value " + floatRegisterFile[i].value + " tag " + floatRegisterFile[i].tag);
        }
        System.out.println("integer Registers File");
         for (int i = 0; i < 32; i++) {
            System.out.println("Register " + i + " value " + integerRegisterFile[i].value + " tag " + integerRegisterFile[i].tag);
        }
    }
}
