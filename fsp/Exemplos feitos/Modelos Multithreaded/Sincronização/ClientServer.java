import java.lang.Runnable;
import java.lang.Thread;

class ClientServer{
	Client objClient;
	Thread objThreadClient;
	Server objServer;
	Thread objThreadServer;
	public void start(){
		try{
			objClient = new Client();
			objThreadClient = new Thread(new ClientRunnable(objClient));
			objServer = new Server();
			objThreadServer = new Thread(new ServerRunnable(objServer));
			objThreadClient.start();
			objThreadServer.start();
			objThreadClient.join();
			objThreadServer.join();
		}catch(Exception e){
			 e.printStackTrace();
		}
	}

	public static void main (String args []){
		ClientServer objClientServer = new ClientServer();
		objClientServer.start();
	}
}
