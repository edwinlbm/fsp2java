import java.util.ArrayList;

class ComposicaoParalelaData{
    private String nomeComposicao;    
    private ArrayList< ArrayList<DadosParalelos> > arrayLista;
    
    public ComposicaoParalelaData(){
        this.nomeComposicao = "";
        this.arrayLista = new ArrayList<>();
    }

    public void setNomeComposicao(String nomeComposicao){
        this.nomeComposicao = nomeComposicao;
    }

    public String getNomeComposicao(){
        return this.nomeComposicao;
    }
    
    public ArrayList<ArrayList<DadosParalelos>> getArrayComposicao(){
        return this.arrayLista;
    }
}