class ClockRunnable implements Runnable{
	
	/*Objeto da classe Clock*/
	Clock objClock;

	ClockRunnable(Clock e){
		/*O objeto passa a ter a referência do objeto definido no método start() na classe principal*/
		objClock = e;
	}

	/*Excução a ação propriamente dita do modelo*/
	public void run() {
		try{
			while(true){
				objClock.tick();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}