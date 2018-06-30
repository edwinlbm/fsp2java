import java.util.ArrayList;

public class DadosRepeticao {
	private String indice;
	private int valorIndice;
	private String limiteInferior;
	private String limiteSuperior;
	private ArrayList<CondicaoOperacao> condicaoOperacao;	
	private String nomeProcesso;
	private ArrayList<Pares> procParam;
	
	/**
	 * @param indice
	 * @param limiteInferior
	 * @param limiteSuperior
	 * @param condicaoOperacao
	 * @param nomeProcesso
	 */
	public DadosRepeticao() {
		this.indice = null;
		this.valorIndice = 0;
		this.limiteInferior = null;
		this.limiteSuperior = null;
		this.condicaoOperacao = new ArrayList<CondicaoOperacao>();
		this.procParam = new ArrayList<Pares>();
		this.nomeProcesso = null;
	}

	/**
	 * @return the indice
	 */
	public String getIndice() {
		return indice;
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
		return valorIndice;
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
		return limiteInferior;
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
		return limiteSuperior;
	}

	/**
	 * @param limiteSuperior the limiteSuperior to set
	 */
	public void setLimiteSuperior(String limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	/**
	 * @return the condicaoOperacao
	 */
	public ArrayList<CondicaoOperacao> getCondicaoOperacao() {
		return condicaoOperacao;
	}

	/**
	 * @param condicaoOperacao the condicaoOperacao to set
	 */
	public void setCondicaoOperacao(ArrayList<CondicaoOperacao> condicaoOperacao) {
		this.condicaoOperacao = condicaoOperacao;
	}

	/**
	 * @return the nomeProcesso
	 */
	public String getNomeProcesso() {
		return nomeProcesso;
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
		return procParam;
	}

	/**
	 * @param procParam the procParam to set
	 */
	public void setProcParam(ArrayList<Pares> procParam) {
		this.procParam = procParam;
	}

}
