import java.lang.Runnable;
import java.lang.Thread;

class Drinks{

    public void coffee(){
        System.out.println("coffee");
    }

    public void red(){
        System.out.println("red");
        coffee();
    }
}

class MainDrinks implements Runnable{
    Thread threadObj;
    Drinks drinks = new Drinks();

    public void start(){
        threadObj = new Thread(this);
        threadObj.start();
    }

    public void run(){
        while(true){
            drinks.red();
        }
    }

    public static void main (String args []){
        MainDrinks main = new MainDrinks();
        main.start();
    }
}