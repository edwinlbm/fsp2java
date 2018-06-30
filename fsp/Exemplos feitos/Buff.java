import java.lang.Runnable;
import java.lang.Thread;

class Buff implements Runnable{
    Thread threadObj;
    int N = 3; //foi definido uma constante N que não é informada no FSP
    int i;

    public void in(int value){
        System.out.println("Valor de entrada: " + value);
    }

    public void out(int value){
        System.out.println("Valor de saida: " + value);

    }

    public void start(){
        threadObj = new Thread(this);
        i = 0;
        threadObj.start();
    }

    public void run(){
        while(true){            
            if(i<=N){
                in(i);//Nao eh informado, ter de assumir por padrão como for um loop basico que não envolva recursão
                out(i);
                i=i+1;
            }else{
                threadObj = null;
                return;
            }
        }
    }

    public static void main(String args[]){
        Buff buff = new Buff();
        buff.start();
    }
}