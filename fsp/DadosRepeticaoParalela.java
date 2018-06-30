import java.util.ArrayList;

public class DadosRepeticaoParalela {
	private String indice;
	private int valorIndice;
	private String limiteInferior;
	private String limiteSuperior;
    private String nomeProcesso;
    
    private String objNomeAcao;
    private String indiceObjRepeticao;
    private String nomeProcessoRepeticao;


	private ArrayList<Pares> procParam;
	
	/**
	 * @param indice
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @param condicaoOperacao
	 * @param nomeProcesso
	 */
	public DadosRepeticaoParalela() {
		this.indice = null;
		this.valorIndice = 0;
		this.limiteInferior = null;
		this.limiteSuperior = null;
		this.procParam = null;
        this.nomeProcesso = null;
        this.objNomeAcao = null;
        this.indiceObjRepeticao = null;
        this.nomeProcessoRepeticao = null;
	}

	/**
	 * @return the indice
	 */
	public String getIndice() {
		return this.indice;
	}

	/**
	 * @param indice the indice to set
	 */
	public void setIndice(String indice) {
		this.indice = indice;
	}

	/**
	 * @return the valorIndice
	 */
	public int getValorIndice() {
		return this.valorIndice;
	}

	/**
	 * @param valorIndice the valorIndice to set
	 */
	public void setValorIndice(int valorIndice) {
		this.valorIndice = valorIndice;
	}

	/**
	 * @return the limiteInferior
	 */
	public String getLimiteInferior() {
		return this.limiteInferior;
	}

	/**
	 * @param limiteInferior the limiteInferior to set
	 */
	public void setLimiteInferior(String limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	/**
	 * @return the limiteSuperior
	 */
	public String getLimiteSuperior() {
		return this.limiteSuperior;
	}

	/**
	 * @param limiteSuperior the limiteSuperior to set
	 */
	public void setLimiteSuperior(String limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	
	

	/**
	 * @return the nomeProcesso
	 */
	public String getNomeProcesso() {
		return this.nomeProcesso;
	}

	/**
	 * @param nomeProcesso the nomeProcesso to set
	 */
	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}

	/**
	 * @return the procParam
	 */
	public ArrayList<Pares> getProcParam() {
		return this.procParam;
	}

	/**
	 * @param procParam the procParam to set
	 */
	public void setProcParam(ArrayList<Pares> procParam) {
		this.procParam = procParam;
    }
        	
	public void setObjNomeAcao(String objNomeAcao) {
		this.objNomeAcao = objNomeAcao;
    }

    public String getObjNomeAcao(){
        return this.objNomeAcao;
    }

    public void setIndiceObjRepeticao(String indiceObjRepeticao){
        this.indiceObjRepeticao = indiceObjRepeticao;
    }

    public String getIndiceObjRepeticao(){
        return this.indiceObjRepeticao;
    }

    public void setNomeProcessoRepeticao(String nomeProcessoRepeticao){
        this.nomeProcessoRepeticao = nomeProcessoRepeticao;
    }

    public String getNomeProcessoRepeticao(){
        return this.nomeProcessoRepeticao;
    }  


}
