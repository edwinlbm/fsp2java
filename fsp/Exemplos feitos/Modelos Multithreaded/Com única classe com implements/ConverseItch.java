import java.lang.Runnable;
import java.lang.Thread;

class ConverseItch implements Runnable{
	Itch objItch;
	Converse objConverse;
	Thread objThread;
	public void start(){
		objItch = new Itch();
		objConverse = new Converse();
		objThread = new Thread(this);
		objThread.start();
	}

	public void run(){
		try{
			while(true){
				objConverse.think();
				objConverse.talk();
				objItch.scratch();
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
	}

	public static void main (String args []){
		ConverseItch objConverseItch = new ConverseItch();
		objConverseItch.start();
	}
}
