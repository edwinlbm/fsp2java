import java.util.ArrayList;

public class DadosExecucao {
	private String nomeProcesso;
	private ArrayList<EscolhaCaminho> arrayEscolha;
	private String nomeSubprocesso;
		
	/**
	 * @param nomeProcesso
	 * @param arrayEscolha
	 * @param nomeSubprocesso
	 */
	public DadosExecucao(String nomeProcesso, ArrayList<EscolhaCaminho> arrayEscolha ,String nomeSubprocesso) {
		this.nomeProcesso = nomeProcesso;
		this.arrayEscolha = new ArrayList<EscolhaCaminho>(arrayEscolha);
		this.nomeSubprocesso = nomeSubprocesso;
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
	 * @return the arrayEscolha
	 */
	public ArrayList<EscolhaCaminho> getArrayEscolha() {
		return arrayEscolha;
	}

	/**
	 * @param arrayEscolha the arrayEscolha to set
	 */
	public void setArrayEscolha(ArrayList<EscolhaCaminho> arrayEscolha) {
		this.arrayEscolha = arrayEscolha;
	}

	/**
	 * @return the nomeSubprocesso
	 */
	public String getNomeSubprocesso() {
		return nomeSubprocesso;
	}

	/**
	 * @param nomeSubprocesso the nomeSubprocesso to set
	 */
	public void setNomeSubprocesso(String nomeSubprocesso) {
		this.nomeSubprocesso = nomeSubprocesso;
	}	
}
