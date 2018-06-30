class Relabelling{
    private String novoRotulo;
    private String rotuloAntigo;

    public Relabelling(String novoRotulo, String rotuloAntigo){
        this.novoRotulo = novoRotulo;
        this.rotuloAntigo = rotuloAntigo;
    }

    public String getNovoRotulo(){
        return this.novoRotulo;
    }

    public String getRotuloAntigo(){
        return this.rotuloAntigo;
    }
}