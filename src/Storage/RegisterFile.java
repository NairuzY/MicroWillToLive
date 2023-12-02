package Storage;

public class RegisterFile {
  public static  Register [] registerFile;
    public RegisterFile(){
    registerFile= new Register[32];

    for(int i=0;i<32;i++){
        registerFile[i]=new Register();
    }}
}
