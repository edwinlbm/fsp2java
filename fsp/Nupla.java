import java.util.ArrayList;

class Nupla{
    private String chave;
    private ArrayList<String> valor;
    private String obj;

    public Nupla(String chave, String obj){
        this.chave = chave;
        this.obj = obj;
        this.valor = new ArrayList<String>();
    }

    public String getChave(){
        return this.chave;
    }

    public void setChave(String novaChave){
        this.chave = novaChave;
    }

    public String getObj(){
        return this.obj;
    }

    public void setObj(String novoObj){
        this.obj = novoObj;
    }

    public ArrayList<String> getValor(){
        return this.valor;
    }

    public void setValor(ArrayList<String> novoValor){
        this.valor = novoValor;
    }
}