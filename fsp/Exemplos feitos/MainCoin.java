import java.lang.Runnable;
import java.lang.Thread;
import java.util.Scanner;

class Tails{
    public void tails(){
        System.out.println("tails");
        // Coin objCoin = new Coin();
    }
}


class Heads{
    public void heads(){
        System.out.println("heads");        
        // Coin coinObj = new Coin(); //O while faz o papel de chamar dnv
        // coinObj.toss();
    }
}

class Coin {
    public void toss(){        
        System.out.println("toss");

        // Scanner sc = new Scanner(System.in);
        // System.out.println("Escolha: Heads  ou Tails");
        // String escolha = sc.next();
        // if(escolha.equals("Heads")){
            Heads headsObj = new Heads();
            headsObj.heads();
        // }
        // if(escolha.equals("Tails")){
            // Tails tailsObj = new Tails();
            // tailsObj.tails();
        // }           
    }
}

class MainCoin implements Runnable{
    Thread objThread;
    Coin coinObj = new Coin();
//Isolar, nem sempre o processo principal pode ser a classe principal
    
    public void start(){
        objThread = new Thread(this);
        objThread.start();
    }

    public void run(){
        while(true){
            coinObj.toss();
            try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}

        }
    }

    public static void main(String args[]){
        MainCoin mainCoin = new MainCoin();
        System.out.println("Thread: 1");
        mainCoin.start();
    }    
}