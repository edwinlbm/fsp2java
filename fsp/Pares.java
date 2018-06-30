class Pares{
    private String prim;
    private String ult;

    public Pares(String primeiro, String ultimo){
        this.prim = primeiro;
        this.ult = ultimo;
    }

    public String getPrim(){
        return this.prim;
    }

    public void setPrim(String novoPrim){
        this.prim = novoPrim;
    }
    
    public String getUlt(){
        return this.ult;
    }

    public void setUlt(String novoUlt){
        this.ult = novoUlt;
    }
}