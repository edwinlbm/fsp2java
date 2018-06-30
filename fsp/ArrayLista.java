import java.util.ArrayList;

class ArrayLista{
    private String chave;
    private ArrayList<String> valor;

    public ArrayLista(String chave){
        this.chave = chave;
        this.valor = new ArrayList<String>();
    }

    public String getChave(){
        return this.chave;
    }

    public void setChave(String novaChave){
        this.chave = novaChave;
    }
    
    public ArrayList<String> getValor(){
        return this.valor;
    }

    public void setValor(ArrayList<String> novoValor){
        this.valor = novoValor;
    }
}