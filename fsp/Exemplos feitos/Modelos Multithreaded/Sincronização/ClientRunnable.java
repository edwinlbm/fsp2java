class ClientRunnable implements Runnable{
	Client objClient;
	Sincronizados objSync;
	ClientRunnable(Client e){
		objClient = e;
		objSync = new Sincronizados();
	}

	public void run(){
		try{
			while(true){
				Thread.sleep(1000);
				objSync.call();								
				
				// objSync.reply();
				// notify();
				objClient.continuee();
				break;
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
	}
}
