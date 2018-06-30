import java.lang.Runnable;
import java.lang.Thread;

class MainClass implements Runnable{
	Thread objThread;
	Countdown countdown$;

	public void start(){
		countdown$ = new Countdown();
		objThread = new Thread(this);
		objThread.start();
	}

	public void run(){
		try{
			while(true){
				countdown$.tick();
				countdown$.tick();
				countdown$.tick();
				countdown$.beep();
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
