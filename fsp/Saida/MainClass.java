//Classe Principal
import java.lang.Runnable;
import java.lang.Thread;
class MainClass implements Runnable{
	Thread objThread;
	Count count$;
	public void start(){
		count$ = new Count();
		objThread = new Thread(this);
		objThread.start();
	}
	public void run(){
		try{
			while(true){
				count$.inc();
				count$.inc();
				return;
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
	}
	public static void main (String args []){
		MainClass objMainClass = new MainClass();
		objMainClass.start();
	}
}





















