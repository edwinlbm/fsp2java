class Count2{
    public void _init_(){
        int i = 0; 
        int N = 3;
    
        while(true){
            if(i < N){
                inc();
                i = i+1;
            }
            if(i > 0){
                dec();
                i = i-1;
            }
        }
    }
    
    public void inc(){
        System.out.println("inc");
    }
    
    public void dec(){
        System.out.println("dec");
    }
}
