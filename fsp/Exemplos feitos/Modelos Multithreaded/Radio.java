/*Classe referente ao modelo*/
class Radio{
	public void off(){
		System.out.println("off");
	}
	public void on(){
		System.out.println("on");
		off();
	}
}
