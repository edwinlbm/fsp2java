class DadosLoop {
    String ordemIntervalo;
    String indiceVal;
    Pares constanteVal;
    String condicaoVal;
    String novoIndice;

    public DadosLoop(){
        this.ordemIntervalo = "";
        this.indiceVal = "";
        this.constanteVal = null;
        this.condicaoVal = "";
        this.novoIndice = "";
    }

    public void clear(){
        this.ordemIntervalo = "";
        this.indiceVal = "";
        // this.constanteVal = null;
        this.condicaoVal = "";
        this.novoIndice = "";        
    }

    public String getOrdemIntervalo() {
        return ordemIntervalo;
    }

    public void setOrdemIntervalo(String ordemIntervalo) {
        this.ordemIntervalo = ordemIntervalo;
    }

    public String getIndiceVal() {
        return indiceVal;
    }

    public void setIndiceVal(String indiceVal) {
        this.indiceVal = indiceVal;
    }

    public Pares getConstanteVal() {
        return constanteVal;
    }

    public void setConstanteVal(Pares constanteVal) {
        this.constanteVal = constanteVal;
    }

    public String getCondicaoVal() {
        return condicaoVal;
    }

    public void setCondicaoVal(String condicaoVal) {
        this.condicaoVal = condicaoVal;
    }

    public String getNovoIndice() {
        return novoIndice;
    }

    public void setNovoIndice(String novoIndice) {
        this.novoIndice = novoIndice;
    }
}