import java.lang.Runnable;
import java.lang.Thread;

class Switch{

    public void off(){
        System.out.println("off");
    }

    public void on(){
        System.out.println("on");
        off();
    }
}

class MainSwitch implements Runnable{
    Thread threadObj;
    Switch switchObj;

    public void start(){
        switchObj = new Switch();
        threadObj = new Thread(this);
        threadObj.start();
    }

    public void run(){
        while(true){
            switchObj.on();
        }
    }

    public static void main(String args []){
        MainSwitch main = new MainSwitch();
        main.start();
    }
}