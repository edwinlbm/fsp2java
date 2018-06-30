class Countdown{

	public void _init_(){
		System.out.println("_init_");
		
		int i = 4;
		int N = 3;
		while(true){
			if(i>=0){
				tick();
				i = i+1;
			}
			if(i==N){
				beep();
				i = i+1;
			}
		}
	}

	public void tick(){
		System.out.println("tick");
	}

	public void beep(){
		System.out.println("beep");
	}
}
