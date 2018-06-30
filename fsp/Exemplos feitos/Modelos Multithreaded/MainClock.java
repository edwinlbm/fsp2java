import java.lang.Runnable;
import java.lang.Thread;

class MainClock{
	/*Objetos das classes a serem trabalhadas*/
	Clock objClock;
	Radio objRadio;

	/*Objetos do tipo Thread*/
	Thread objThreadClock;
	Thread objThreadRadio;

	public void start(){
		/*Inicialização dos objetos*/
		objClock = new Clock();
		objRadio = new Radio();

		/*Inicialização dos objetos Thread que recebem como parâmetro uma classe que 
		implementa a iterface Runnable que por sua vez tem como parâmetro um objeto (da classe Radio ou Clock)*/
		objThreadClock = new Thread(new ClockRunnable(objClock));
		objThreadRadio = new Thread(new RadioRunnable(objRadio));

		/*Disparo das Threads*/
		objThreadClock.start();
		objThreadRadio.start();
	}
    
	public static void main(String args[]){		
		/*Inicialização da classe principal*/
		MainClock objClock = new MainClock();
		/*Chama o método start */
		objClock.start();					
	}
}
