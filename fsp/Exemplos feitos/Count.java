import java.lang.Runnable;
import java.lang.Thread;

class Count implements Runnable{
    Thread objThread = null;
    int N = 3;
    int i;

    public void inc(int value){
        System.out.println("inc: " + value);
    }

    public void dec (int value){
        System.out.println("dec: " + value);
    }

    public void start(){
        objThread = new Thread(this);
        i = 0;//Tem de detectar no PROCESSO[0], [i:0..N] nesse caso o "i" inicia de 0 e vai até N 
        objThread.start();
    }

    //Está variando entre 0 e 1 porque o usuario que deveria escolhar a opção
    public void run(){
        while(true){
            if(i < N){
                inc(i+1);
                i = i+1;//Eh informado como um indice do processe                
            }
            // try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
            if(i > 0){
                dec(i-1);
                i = i - 1;
            }
        }
    }

    public static void main(String args []){
        Count count = new Count();
        count.start();   
    }
}