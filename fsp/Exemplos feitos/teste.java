public class teste{
    public void a(){
        b();
    }

    public void b(){
        System.out.println("Entrei no b");
        c();
    }
    public void c(){
        System.out.println("Entrei no c\nFim.");
        
    }

    public static void main(String args []){
        teste go = new teste();
        go.a();
    }    
}