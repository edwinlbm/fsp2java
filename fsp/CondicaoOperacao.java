public class CondicaoOperacao {
	private String nomeAcao;
	private Condicao condicao;
	private Condicao operacao;
	private String proxProcesso;
	/**
	 * @param nomeAcao
	 * @param condicao
	 * @param operacao
	 * @param proxProcesso
	 */
	
	public CondicaoOperacao() {
		this.nomeAcao = null;
		this.condicao = null;
		this.operacao = null;
		this.proxProcesso = null;
	}
	
	/**
	 * @return the nomeAcao
	 */
	public String getNomeAcao() {
		return nomeAcao;
	}
	/**
	 * @param nomeAcao the nomeAcao to set
	 */
	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}
	/**
	 * @return the condicao
	 */
	public Condicao getCondicao() {
		return condicao;
	}
	/**
	 * @param condicao the condicao to set
	 */
	public void setCondicao(Condicao condicao) {
		this.condicao = condicao;
	}
	/**
	 * @return the operacao
	 */
	public Condicao getOperacao() {
		return operacao;
	}
	/**
	 * @param operacao the operacao to set
	 */
	public void setOperacao(Condicao operacao) {
		this.operacao = operacao;
	}
	/**
	 * @return the proxProcesso
	 */
	public String getProxProcesso() {
		return proxProcesso;
	}
	/**
	 * @param proxProcesso the proxProcesso to set
	 */
	public void setProxProcesso(String proxProcesso) {
		this.proxProcesso = proxProcesso;
	}	
}