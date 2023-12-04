package Storage;

public class RegisterFile {
    public static Register[] registerFile;
    
    public RegisterFile() {
        registerFile = new Register[32];
        
        for (int i = 0; i < 32; i++) {
            registerFile[i] = new Register(i + 1);
        }
    }
    
    public static void print() {
        for (int i = 0; i < 32; i++) {
            System.out.println("Register " + i + " value " + registerFile[i].value + " tag " + registerFile[i].tag);
        }
    }
}
