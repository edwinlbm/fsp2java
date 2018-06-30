class Sincronizados{
	// synchronized public void reply() throws InterruptedException{
	// 	System.out.println("reply");
	// 	wait();
	// 	notify();
	// }

	public synchronized void call() throws InterruptedException{
		
		System.out.println("call");
		wait();
		notify();
	}
}