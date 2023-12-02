package Storage;

public class Register {
    public float value;
    public String tag;
    public Register(){
        tag=null;
        value=0;
    }

    public Register(float value){
        tag=null;
        this.value=value;
    }
    public void setRegisterValue(float value){
        this.value=value;
    }
     public void setRegisterTag(String tag){
        this.tag=tag;
    }

 }
