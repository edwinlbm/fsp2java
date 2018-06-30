import java.util.ArrayList;

class Escolhas{
    public String chave;
    public ArrayList<ArrayLista> arrayLista;//Se size maior que 1, há escolha

    public Escolhas(){
        this.chave = null;
        this.arrayLista = new ArrayList<ArrayLista>();
    }    
}