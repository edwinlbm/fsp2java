class RadioRunnable implements Runnable{

	/*Objeto da classe Radio*/
	Radio objRadio;	

	RadioRunnable(Radio e){
		/*O objeto passa a ter a referência do objeto definido no método start() na classe principal*/
		objRadio = e;
	}

	/*Excução a ação propriamente dita do modelo*/
	public void run(){
		try{
			while(true){
				objRadio.on();	
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}