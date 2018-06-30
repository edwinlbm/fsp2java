class ServerRunnable implements Runnable{
	Server objServer;
	Sincronizados objSync;

	ServerRunnable(Server e){
		objServer = e;
		objSync = new Sincronizados();
	}
	public void run(){
		try{
			while(true){				
				Thread.sleep(20);
				objSync.call();	
				
				objServer.service();
				// wait();
				// objSync.reply();
				// notify();				
				break;				
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
	}
}
